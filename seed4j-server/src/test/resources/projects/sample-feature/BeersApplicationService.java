package com.seed4j.growth.dummy.application;

import com.seed4j.growth.dummy.domain.BeerId;
import com.seed4j.growth.dummy.domain.beer.Beer;
import com.seed4j.growth.dummy.domain.beer.BeerToCreate;
import com.seed4j.growth.dummy.domain.beer.Beers;
import com.seed4j.growth.dummy.domain.beer.BeersCreator;
import com.seed4j.growth.dummy.domain.beer.BeersRemover;
import com.seed4j.growth.dummy.domain.beer.BeersRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class BeersApplicationService {

  private final BeersRepository beers;
  private final BeersCreator creator;
  private final BeersRemover remover;

  public BeersApplicationService(BeersRepository beers) {
    this.beers = beers;

    creator = new BeersCreator(beers);
    remover = new BeersRemover(beers);
  }

  @PreAuthorize("can('create', #beerToCreate)")
  public Beer create(BeerToCreate beerToCreate) {
    return creator.create(beerToCreate);
  }

  @PreAuthorize("can('remove', #beer)")
  public void remove(BeerId beer) {
    remover.remove(beer);
  }

  public Beers catalog() {
    return beers.catalog();
  }
}
