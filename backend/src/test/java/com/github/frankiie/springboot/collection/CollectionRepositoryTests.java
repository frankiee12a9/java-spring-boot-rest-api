package com.github.frankiie.springboot.collection;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.frankiie.springboot.domains.collection.entity.Collection;
import com.github.frankiie.springboot.domains.collection.repository.CollectionRepository;
import com.github.frankiie.springboot.domains.collection_image.entity.Image;
import com.github.frankiie.springboot.domains.collection_note.entity.Note;
import com.github.frankiie.springboot.domains.collection_note_comment.entity.Comment;

/**
 * Integration test for executing finders, thus testing various query lookup strategies.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "classpath:config/namespace-application-context.xml")
@Transactional
public class CollectionRepositoryTests {
  	@PersistenceContext EntityManager em;

    @Autowired private CollectionRepository collectionRepository;

    private Collection collection;
    private Note note;
    private Image image;
    private Comment comment;

    @BeforeEach
    void setUp() throws Exception {
      // firstUser = new User("Oliver", "Gierke", "gierke@synyx.de");
      // firstUser.setAge(28);
      // secondUser = new User("Joachim", "Arrasz", "arrasz@synyx.de");
      // secondUser.setAge(35);
      // Thread.sleep(10);
      // thirdUser = new User("Dave", "Matthews", "no@email.com");
      // thirdUser.setAge(43);
      // fourthUser = new User("kevin", "raymond", "no@gmail.com");
      // fourthUser.setAge(31);
      // adminRole = new Role("admin");

      SecurityContextHolder.clearContext();
	}

    void flushTestUsers() {
      // em.persist(adminRole);

      // firstUser = repository.save(firstUser);
      // secondUser = repository.save(secondUser);
      // thirdUser = repository.save(thirdUser);
      // fourthUser = repository.save(fourthUser);

      // repository.flush();

      // id = firstUser.getId();

      // assertThat(id).isNotNull();
      // assertThat(secondUser.getId()).isNotNull();
      // assertThat(thirdUser.getId()).isNotNull();
      // assertThat(fourthUser.getId()).isNotNull();

      // assertThat(repository.existsById(id)).isTrue();
      // assertThat(repository.existsById(secondUser.getId())).isTrue();
      // assertThat(repository.existsById(thirdUser.getId())).isTrue();
      // assertThat(repository.existsById(fourthUser.getId())).isTrue();
    }
}
