package me.woemler.gdw;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * @author woemler
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable>
		extends PagingAndSortingRepository<T, ID>,
		QueryDslPredicateExecutor<T>
{

}
