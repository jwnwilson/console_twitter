# Console Twitter Exercise

To run the application in a terminal run:

./compile.sh

./run.sh

Additional run args

## Verbose mode

./run.sh -v

## Load initial json file

./run.sh -i test_data.json

## Simple Console Twitter clone:

The application has the following commands:

posting: (user name) -> (message)

reading: (user name)

following: (user name) follows (another user)

wall: (user name) wall

Examples in exercise described below.

Exercise
Implement a console-based social networking application (similar to Twitter) satisfying the scenarios below.
Features
Posting: Alice can publish messages to a personal timeline

Alice -> I love the weather today
Bob -> Damn! We lost!
Bob -> Good game though.

Reading: I can view Alice and Bob’s timelines

Alice
I love the weather today (5 minutes ago)
Bob
Good game though. (1 minute ago)
Damn! We lost! (2 minutes ago)

Following: Charlie can subscribe to Alice’s and Bob’s timelines, and view an aggregated list of all subscriptions

Charlie -> I'm in New York today! Anyone want to have a coffee?
Charlie follows Alice
Charlie wall
Charlie - I'm in New York today! Anyone want to have a coffee? (2 seconds ago)
Alice - I love the weather today (5 minutes ago)

Charlie follows Bob
Charlie wall
Charlie - I'm in New York today! Anyone wants to have a coffee? (15 seconds ago)
Bob - Good game though. (1 minute ago)
Bob - Damn! We lost! (2 minutes ago)
Alice - I love the weather today (5 minutes ago)
