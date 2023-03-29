package com.github.frankiie.springboot.domains.test_foo_bar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FooBarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FooBarService.class);
  
    @Autowired private FooRepository fooRepository;
    @Autowired private BarRepository barRepository;

    public Foo getFooById(Long id) {
      return fooRepository.findById(id).orElse(null);
    }

    public Bar getBarById(Long id) {
      return barRepository.findById(id).orElse(null);
    }

    public Bar saveBar(CreateBar props) {
      var foo = new Foo("foo_1");
      fooRepository.save(foo);

      var bar = new Bar(props);
      barRepository.save(bar);
      bar.getFoos().add(foo);

      foo.getBars().add(bar);
      fooRepository.save(foo);

      return bar;
    }


    public Foo saveFoo(CreateFoo props) {
      var bar = getBarById(props.getBarId());

      var foo1 = new Foo(props);
      fooRepository.save(foo1);
      LOGGER.info("bar.getFoos(1) " + bar.getFoos());
      bar.getFoos().add(foo1);

      barRepository.save(bar);
      LOGGER.info("bar.getFoos(2) " + bar.getFoos());
      
      return foo1;
    }

}
