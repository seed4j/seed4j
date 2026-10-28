# Sample feature

It looks like you added a sample feature to your project...

This feature is a simple business example and exercise for this kind of projects. Here's the business context:

A brave beer retailer is handling his three shops with papers and pencils and he is starting to have some trouble scaling up. He asked us to build a simple system where he'll be able to:

- Add new beers (with name and price);
- Remove beers from selling;
- Get a catalog of currently sold beers to display in the shops.

Our brave team of one dev didn't build that on his own, in fact he was with the retailer (tasting the product) while doing the first version. As the goal was to show what can be done easily the persistence is in memory for now, but hopefully we'll be able to change that later.

Even this basic product already helped the retailer (a lot)! So he came back to our team (with some beers!) to add a feature to manage orders. The team started something to allow orders taking but this time the retailer had to leave and the team was stuck! What's an order??? Is it something going on with a customer in the shop or is it something "done" received from somewhere else?

We got answer from the retailer later on but our team is currently recovering from a very serious headache. Can you end what they started on order considering that:

- There are running orders, with a customer in the shop adding beers to the order;
- Validated orders (when they are paid), they can't move but we need to keep them (without any price change).
