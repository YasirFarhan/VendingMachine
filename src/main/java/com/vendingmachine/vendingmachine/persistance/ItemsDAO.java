package com.vendingmachine.vendingmachine.persistance;

import com.vendingmachine.vendingmachine.model.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsDAO extends CassandraRepository<Item, Integer> {

}
