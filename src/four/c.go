package main

import (
	"math/rand"
	"sync"
	"time"
)

type City struct {
	index      int
	deleted    bool
	routePrice int
}

func ChangePrice(adjList [][]City, rwLock *sync.RWMutex) {
	for true {
		rand.Seed(time.Now().UnixNano())
		randFrom := rand.Intn(len(adjList))

		rwLock.Lock()

		if len(adjList[randFrom]) != 0 {

			randToIndex := rand.Intn(len(adjList[randFrom]))
			cityTo := adjList[randFrom][randToIndex]

			newPrice := 1 + rand.Intn(100)
			randTo := cityTo.index
			cityTo.routePrice = newPrice

			println("Price", randFrom, " <-> ", randTo, "was changed to", newPrice)

			for i := 0; i < len(adjList[randTo]); i++ {
				if adjList[randTo][i].index == randFrom {
					cityFrom := adjList[randTo][i]
					cityFrom.routePrice = newPrice
					break
				}
			}
		}
		rwLock.Unlock()
		time.Sleep(2 * time.Second)
	}
}

func editRoute(adjList [][]City, cityState []City, rwLock *sync.RWMutex) {
	for true {
		rand.Seed(time.Now().UnixNano())
		randFrom := rand.Intn(len(adjList))

		rwLock.Lock()

		if len(adjList[randFrom]) != 0 && !cityState[randFrom].deleted {
			action := rand.Intn(2) == 0

			randToIndex := rand.Intn(len(adjList[randFrom]))
			cityTo := adjList[randFrom][randToIndex]

			cityTo.deleted = action
			randTo := cityTo.index

			if action {
				println("Route", randFrom, " <-> ", randTo, "was deleted")
			} else {
				println("Route", randFrom, " <-> ", randTo, "was added")
			}

			for i := 0; i < len(adjList[randTo]); i++ {
				if adjList[randTo][i].index == randFrom {
					cityFrom := adjList[randTo][i]
					cityFrom.deleted = action
					break
				}
			}
		}
		rwLock.Unlock()
		time.Sleep(3 * time.Second)
	}
}

func dfs(adjList [][]City, cityState []City, rwLock *sync.RWMutex) {
	for true {
		rand.Seed(time.Now().UnixNano())
		var randFrom int
		var randTo int
		rwLock.RLock()
		for {
			randFrom = rand.Intn(len(adjList))
			randTo = rand.Intn(len(adjList))

			if randFrom != randTo && !cityState[randFrom].deleted && !cityState[randTo].deleted {
				break
			}
		}

		visited := make([]bool, len(cityState))
		price := dfsHelper(adjList, cityState, &cityState[randFrom], &cityState[randTo], visited)
		println("Price from", randFrom, "to", randTo, "is", price)
		rwLock.RUnlock()
		time.Sleep(2 * time.Second)
	}
}

func dfsHelper(adjList [][]City, cityState []City, cur *City, dest *City, visited []bool) int {
	if cur.index == dest.index {
		return 0
	}
	visited[cur.index] = true
	for i := 0; i < len(adjList[cur.index]); i++ {
		neighbour := adjList[cur.index][i]
		if !visited[neighbour.index] {
			neighbourPrice := dfsHelper(adjList, cityState, &neighbour, dest, visited)
			if neighbourPrice != -1 {
				return neighbourPrice + neighbour.routePrice
			}
		}
	}
	return -1
}

func main() {
	var RWLock sync.RWMutex

	adjList := [][]City{
		{City{1, false, 15}, City{2, false, 8}},
		{City{0, false, 15}, City{2, false, 19}, City{4, false, 2}},
		{City{0, false, 8}, City{1, false, 19}, City{3, false, 3}, City{4, false, 33}},
		{City{2, false, 3}, City{4, false, 25}},
		{City{1, false, 2}, City{2, false, 33}, City{3, false, 25}},
	}
	cityState := []City{
		{0, false, 0},
		{1, false, 0},
		{2, false, 0},
		{3, false, 0},
		{4, false, 0},
	}

	go ChangePrice(adjList, &RWLock)
	go editRoute(adjList, cityState, &RWLock)
	go dfs(adjList, cityState, &RWLock)

	time.Sleep(1 * time.Hour)
}
