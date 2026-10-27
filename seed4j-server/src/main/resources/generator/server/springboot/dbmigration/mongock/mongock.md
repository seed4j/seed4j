# Mongock

[Mongock](https://mongock.io/) is a migration tool used to manage NoSQL databases lifecycle.

To do that you'll need to define `@ChangeUnit` in your application, here's a simple example:

```java
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.index.TextIndexDefinition.TextIndexDefinitionBuilder;

@ChangeUnit(id = "create-beers-collection", order = "002", author = "seed4j")
public class BeersCollectionChangeUnit {

  @Execution
  public void createCollection(MongoTemplate mongo) {
    mongo.createCollection(BeerDocument.class);

    TextIndexDefinition indexDefinition = new TextIndexDefinitionBuilder().onField("selling_state").build();
    mongo.indexOps(BeerDocument.class).createIndex(indexDefinition);
  }

  @RollbackExecution
  public void dropCollection(MongoTemplate mongo) {
    mongo.dropCollection(BeerDocument.class);
  }
}
```

> Sadly, the classes annotated with `@ChangeUnit` must be public

As transactions are not ensured on NoSQL databases each Execution comes with its rollback counterpart.
