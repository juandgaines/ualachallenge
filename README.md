# Challenge Search Places Module

This module provides an autocomplete search feature for city names, balancing performance and memory use through a combination of local database queries and an in-memory Trie.

---


## Problem Statement

We need to provide users with a fast, responsive autocomplete for city names by filtering results based on a given prefix string. The requirements are:

- **Prefix definition**: Match the initial characters of the city name (case insensitive).
- **Performance focus**: Optimize search latency; initial data load time is secondary.
- **Case insensitivity**: The search must ignore letter case.


## Overview
**Problem:** Real-time autocomplete for city names without loading the entire dataset into memory (which could lead to out-of-memory errors).

**Approach:**
1. **Persist** city data in a Room database when the application first runs.
2. **On each keystroke**, query the database using SQL `LIKE prefix%` to retrieve only the subset of cities matching the current input.
3. **Load that subset** into an in-memory Trie (prefix tree) for fast lookup.
4. **Optimize** incremental updates: if a new prefix extends the previous one, reuse the existing Trie branch; otherwise, clear and rebuild only when necessary.

---

## Architecture

1. **RemoteCitiesDataSource**
    - Fetches `List<CitiesDto>` via Retrofit from remote endpoint provided in the challenge.
    - Converts data transfer objects into domain `City` objects.

2. **LocalCitiesDataSource**
    - Defines `PlacesDao` for Room operations: prefix queries, favorites filtering, upserts, and retrieval by ID.
    - Exposes methods to fetch, insert, and mark cities as favorite.

3. **CitiesRepository**
    - Coordinates between remote and local sources using injected `AppDispatchers` (IO and Main).
    - On initial query, fetches remote data, persists it locally, and serves subsequent queries from the database.

4. **CityTrie**
    - Implements a standard Trie with `TrieNode` objects containing `children: Map<Char, TrieNode>` and a list of `cities: List<City>`.
    - Provides `insert(city)`, `searchByPrefix(prefix)`, `clear()`, `shouldRebuildFor(prefix)`, and `setLastPrefix(prefix)` methods.

5. **SearchLocationViewModel**
    - Uses `StateFlow` for `_query`, `_favorite`, and an `_update` counter.
    - On query or favorite filter change, retrieves matching cities, updates or rebuilds the Trie as needed, and emits a `SearchState` with suggestions.

---

## Complexity Analysis

Let:
- **N** = total number of cities in the dataset.
- **k** = number of cities matching the current prefix.
- **m** = length of the query string (number of characters).

**Time Complexity:**
- **Database query:** O(log N + k) — using indexed prefix lookups plus sorting k results.
- **Trie insertion:** O(k × m) — inserting each of k cities with m characters.
- **Trie search:** O(m) per lookup — traversing m characters to find the subtree.

**Space Complexity:**
- We store only k cities in the Trie, using O(k × m) space, instead of O(N × m) if we loaded all cities.

---

## Testing Strategy

- **Unit tests**:
    - Fake DAO and Fake API verify local and remote data source logic.
    - Trie unit tests cover insertion, prefix search, and incremental rebuild conditions.
    - Repository tests use `AppTestDispatchers` to simulate dispatchers in unit tests.
    - ViewModel tests use Turbine to validate state emissions.

- **UI tests** (Compose):
    - Use `contentDescription` on semnatics modifier overlays for map markers, since the native GoogleMap marker is not part of the Compose semantics tree.
    - Scroll through `LazyColumn` items and perform clicks based on text or test tags.
- **End-to-end tests** (Compose E2E):
    - Full app flow is tested: typing a prefix, selecting a city from the list, and verifying the map marker overlay appears.
---

This design ensures fast, memory-efficient autocomplete by combining selective database queries with an in-memory Trie optimized for incremental updates.


## Tech Stack

- **Language:** Kotlin 2.1.21
- **UI:** Jetpack Compose Material 3 (androidx.compose.material3)
- **State & Architecture:** Kotlin Coroutines, StateFlow, Clean Architecture
- **Dependency Injection:** Hilt (Dagger)
- **Local Storage:** Room (androidx.room)
- **Networking:** Retrofit, OkHttp, Kotlinx Serialization
- **Maps:** Google Maps Compose, Play Services Maps
- **Testing:** JUnit4, MockK, Mockito, Turbine, Espresso, Compose Testing

---

## Configuration

Before running the app, you need to provide your Google Maps API key. Add the following line to your `local.properties` file in the project root:

```local.properties
MAPS_API_KEY=your_google_maps_api_key_here