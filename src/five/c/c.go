package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func ititialize() {
	var waitGroup sync.WaitGroup

	threads := make([]ArrayManager, 3)
	sums := make([]int, 3)

	for i := 0; i < 3; i++ {
		threads[i] = *(NewArrayManager(100))
		sums[i] = threads[i].getCurrentSum()
	}

	if sums[0] == sums[1] && sums[1] == sums[2] {
		fmt.Printf("Sums of three arays are equal and value is %d\n", sums[0])
		return
	}

	for {
		for i := 0; i < 3; i++ {
			waitGroup.Add(1)

			go func(index int) {
				defer waitGroup.Done()
				threads[index].arrayModification()
			}(i)
		}

		waitGroup.Wait()

		for i := 0; i < 3; i++ {
			sums[i] = threads[i].getCurrentSum()
		}

		if sums[0] == sums[1] && sums[1] == sums[2] {
			for i := 0; i < 3; i++ {
				threads[i].print()
			}

			fmt.Printf("Sums of three arays are equal and value is %d\n", sums[0])
			return
		}
	}
}

func main() {
	rand.Seed(time.Now().UnixNano())
	ititialize()
}
