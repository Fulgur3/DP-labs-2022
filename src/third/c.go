package third


import (
"fmt"
"math/rand"
"sync"
"time"
)

var tobacco bool
var paper bool
var matches bool

var cigarette_constituents = []string{"tobacco", "paper", "matches"}

var cigarette_done []string

func contains(s []string, searchterm string) bool {

	for _, v := range s {
		if v == searchterm {
			return true
		}
	}

	return false
}

func remove(slice []string, value string) []string {

	for k, v := range slice {
		if value == v {
			return append(slice[:k], slice[k+1:]...)
		}
	}
	return slice
}

func manager(wg *sync.WaitGroup) {
	cigarette_done = []string{}
	var f, s int

	f = rand.Int() % len(cigarette_constituents)
	for f == s {
		s = rand.Int() % len(cigarette_constituents)
	}
	cigarette_done = append(cigarette_done, cigarette_constituents[f], cigarette_constituents[s])

	wg.Done()
}

func smoker(who string, wg *sync.WaitGroup) {

	if !contains(cigarette_done, who) {
		fmt.Printf("Now in cigarette %v\n", cigarette_done)

		fmt.Printf("Add " + who + " in cigarette\n")

		fmt.Println("\nsmoking a cigarette")

		fmt.Println("1/3")
		time.Sleep(1 * time.Second)
		fmt.Println("2/3")
		time.Sleep(1 * time.Second)
		fmt.Println("3/3")
		time.Sleep(1 * time.Second)
	}

	wg.Done()
}

func main() {

	var wg sync.WaitGroup

	var mutex sync.Mutex

	for i := 0; i < 3; i++ {
		mutex.Lock()
		wg.Add(1)
		manager(&wg)
		wg.Wait()

		wg.Add(3)
		go smoker("tobacco", &wg)
		go smoker("paper", &wg)
		go smoker("matches", &wg)
		wg.Wait()
		mutex.Unlock()
	}
}
