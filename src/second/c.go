package main

import (
	"math"
	"math/rand"
	"time"
)

func createParticipants(size int) chan int {
	list := make(chan int, size)

	s := rand.NewSource(time.Now().Unix())
	random := rand.New(s)

	for i := 0; i < size; i++ {
		val := random.Intn(150) + 1
		print(val, " ")
		list <- val
	}
	println()
	return list
}

func versus(list chan int, nextRound chan int) {
	opponent1 := <-list
	opponent2 := <-list
	if opponent1 > opponent2 {
		println("Fight", opponent1, "vs", opponent2, ":", opponent1, "wins")
		nextRound <- opponent1
	} else {
		println("Fight", opponent1, "vs", opponent2, ":", opponent2, "wins")
		nextRound <- opponent2
	}
}

func main() {
	const size = 8
	participants := createParticipants(size)

	loops := int(math.Log2(size))
	fights := size / 2

	for i := 1; i <= loops; i++ {
		nextParticipant := make(chan int, fights)
		if i == loops {
			go versus(participants, nextParticipant)
			winner := <-nextParticipant
			println("\nWinner of the fight is:", winner)
			break
		}

		for i := 0; i < fights; i++ {
			go versus(participants, nextParticipant)
		}

		fights /= 2
		participants = nextParticipant
	}
}
