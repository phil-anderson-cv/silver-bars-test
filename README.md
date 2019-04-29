# silver-bars-test
Public repository for Credit Suisse coding test 26/04/19

## Assumptions
With coding tests there's always an element of guesswork when it comes to the fine details. You have to be pragmatic,
and make some assumptions. I hope mine aren't too far out, but all of them are easy to change anyway if they turn out
to be incorrect.

* I've assumed that clients of the API will check the summary more frequently than they will register or cancel orders,
  so producing the summary report should be fast.
* It's not clear whether Buys and Sells should be returned separately in different calls, or together in one. I've chosen
  to return them together in one call, with Buys first.
* There's nothing in the spec indicating that the original un-summarised orders actually need to be retained, as the 
  only output of the code is the summary report, however I do keep them in order to do a sanity-check when doing
  cancellations. To speed this up, I've assumed that it's OK not to store them in order.
* I've assumed you're more interested in evaluating my ability to code than my ability to use libraries, so I've tried
  to make minimal use of them, and mainly for testing (Junit and AssertJ), however I couldn't resist using a MultiSet
  (from Apache Collections) for the un-summarised orders as it's just the exact right tool for the job.

## Performance
Assuming summary performance is the priority (see above), I've chosen to "pay it forward" by maintaining a summarised
TreeMap of the orders as they are registered and cancelled. This makes producing the summary report nice and fast,
with just O(n) complexity, but it comes at the cost of slowing down registering and cancelling orders a little, which 
end up with O(log(n)) complexity.
