# Hexagonal architecture (application service flavor)

Good evening, and welcome to this architecture documentation. We're pleased that you've found your way here! Please note that this documentation may become outdated over time, so feel free to update it as needed.

So, this is basically about why and how to use a peculiar flavor of [hexagonal architecture](https://alistair.cockburn.us/hexagonal-architecture/).

**In a nutshell**, hexagonal architecture:

- Protects the domain model;
- Clearly separates business and infrastructure responsibilities.

## Disclaimers

- This documentation is a [Seed4J](https://github.com/seed4j/seed4j) module. This **probably doesn't fit your exact needs, you'll have to adapt it!**;
- This is one of the many possible implementations of this architecture. If you are not comfortable with this one, just stick to one that helps you;
- Code architectures are here to help us build great software faster, if it's failing there is probably something to change.

## Goals

Here are some of the properties we are looking for when using this kind of architecture. Keeping them in mind while coding is really important!

### Built for changes

"The Only Constant in Life Is Change."- Heraclitus. This can't be more true in software (hence [the name](https://en.wikipedia.org/wiki/Software)) and, if you have just a few weeks of experience, you know that "the users don't know what they want!!!". If the first quote is true the second one is fallacious!

It's not that the users don't know what they want, in fact, nobody knows. Building software is not done with somebody telling the others what to do, it's done with [productive partnerships](https://manifesto.softwarecraftsmanship.org/). Yes, that means people will change their mind times and times again (and this is totally fine!).

So, as professional software developers we have to ensure that the code we are writing can welcome those changes. This often starts by lowering the solution complexity!

We often talk about 3 types of complexity:

- **Essential**: When building software we have to solve a problem of complexity X. This is the essential complexity. This complexity directly relates to the problem we are trying to solve and we can't really make that lower.
- **Mandatory**: No matter the efforts, we are going to have to add some complexity to the system since we have technical stuff to do (persist data, send messages, ...). This complexity is called mandatory complexity.
- **Accidental**: On top of the two previous complexities comes the accidental complexity, the one we don't want because it's not needed. For example, if you have a configuration that has been standing still for the past 10 years, you probably don't need to put that in a database, handling that in code will be easier and more efficient (and no, you probably don't need microservices with one team of 3).

The hexagonal architecture allows us to reduce all those complexities to their bare minimum by giving clear responsibilities to each part of our software.

The very clear separation of concern enforced by the architecture eases automatic testing of each part since each part is only doing one thing. Being able to build solid tests is also a great way to build a change welcoming software!

> Even if the architecture eases tests writing, being able to write good tests takes time and practice!

### Shorten feedback loops

In software development if you want to go faster (like really faster) you'll have to go for short feedback loops. If you have a button that can tell you in a few seconds that your solution is still behaving as intended you'll be way faster than checking that by hand after any update (in fact, you won't check by hand that after any update...).

Let's be honest here: hexagonal architecture won't help for the fastest feedback loops which are pair feedback in [pair](https://en.wikipedia.org/wiki/Pair_programming) or [mob programming](https://en.wikipedia.org/wiki/Mob_programming).

BUT, just after that comes compilation and, for that, hexagonal architecture will help! Thanks to the great Separation Of Concern you'll be able to build modules (packages in Java) with a very high cohesion and very low coupling. That means, most classes in the infrastructure modules will never get out of there hence allowing compilation time feedbacks.

There is another **AWESOME** compilation time feedback coming not directly from the architecture but from a practice often used in that architecture: Types Driven Development. The idea is pretty simple: create a dedicated type for each business concept. Example:

- `Firstname`: Yes, this is a `String` BUT this is not a phone number or a Klingon dictionary so create a type for that (with some checks and formatting).
- `Lastname`: Yep, another `String` BUT... same reasons.

With just those two you can totally fix lots of mistakes with `firstname` and `lastname` inversions in method parameters: if you send the wrong one it just won't compile!

Then, a little bit slower than compilation, comes automated tests. As seen earlier this architecture eases testing so you'll be able to get fast (counting in seconds here) and reliable feedback from tests.

We said earlier that pairs feedbacks were the fastest ones but what about business experts feedback? Using "classic" (Controller, Service, Repository) architecture we have to build a whole "thing" to hope for a business expert feedback. Here, the Domain Model code is so close to the business that it's really easy to sit at a screen with a business expert and to get feedbacks from that! Of course, you'll probably have to explain some "coding stuff" BUT you will be able to get feedback from the business expert on any given piece of algorithm really early!

### Delay infrastructure choices

It's common to start a project with meetings to "build the architecture" which, in that case, means choosing the infrastructure elements. So, on day 0, we are trying to figure out if we need a MongoDB, a PostgreSQL or both (and what about an Elasticsearch?).

The problem is: we often make these choices without enough information and we'll just make our best guesses (since the real needs will come from the code). The other problem is that we spend a lot of time doing that. Another option would be to pick only one thing: the language (do we go Java?). Picking the language can be challenging enough but it's easier than picking a gazillion technologies along with the language.

The hexagonal architecture allows us to start as soon as we know the language. From that, we'll be able to start building a solution and have the real infrastructure needs appear from the code. Of course, we'll have to pick a structuring framework soon enough (Spring, Quarkus, ...) but we can delay the persistence choice for quite some time!

Delaying choices allows:

- Better choices. Even if you say "we'll change if necessary" you'll have to fight against [the sunk cost fallacy](https://thedecisionlab.com/biases/the-sunk-cost-fallacy);
- Faster first loops (since you remove big parts of the mandatory complexity from the bootstrap).

## Where to put code

Finally, the architecture part you were looking for :P.

So, first things first: **an application is made of multiple "hexagons"**, one for each [Bounded Context](https://martinfowler.com/bliki/BoundedContext.html). (Yes, sometimes you can have only one but this is an exception). We usually have each Bounded Context as root packages in the application.

Originally, this architecture was presented in a hexagon (hence the name) with the Domain Model at its center:

![Hexagonal architecture overview](hexagonal-global-schema.png)

In this flavor, the calls flow is as follows:

![Hexagonal architecture flow](hexagonal-flow.png)

We can enforce this architecture with this folder organization:

- `my_business_context`: root package for the context (naming depends on your technology naming conventions)
  - `application`: contains the application layer code
  - `domain`: contains the business code
  - `infrastructure`:
    - `primary`: contains adapters implementations that drive your context
    - `secondary`: contains adapters implementations that your context drives

As said many times, each "part" here has a specific concern so let's follow the rabbit in that hole.

### Code in Domain Model

This is the code that really matters. You can build it using [Domain Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design) building blocks or any other tool that will help you build a clear representation of the business.

This model doesn't depend on anything and everything depends on it, so it is totally framework-agnostic, you just need to pick a language to build your Domain Model.

Apart from the code used to make the business operations we'll find ports in the Domain Model. Ports are `interfaces` that are used to invert dependencies. As the Domain Model sometimes needs ports for some operations, they can only be there since the Domain doesn't depend on anything.

### Code in Application

The application layer **MUST NOT CONTAIN ANY BUSINESS RULE**, its responsibilities are:

- Basic orchestration:
  - Get something from a port;
  - Make an operation on that thing (call a method on the object);
  - Save that thing using a port;
  - Dispatch created events using a port.
- Transactions management;
- Authorization check (this is the wiring point, the business for authorization must be in the domain).

### Code in Primary

The primary part contains adapters for the code driving our domain. Example: code to expose REST WebServices. This part depends a lot on frameworks and is responsible for making the best possible exposure of the business actions.

### Code in Secondary

The secondary part is made of adapters implementing the ports from the domain. This part depends a lot on frameworks and its responsibility is to make the best possible use of the infrastructure our business needs.
