package me.woemler.gdw;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author woemler
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable>
        extends PagingAndSortingRepository<T, ID>,
        QueryDslPredicateExecutor<T>/*,
        QuerydslBinderCustomizer<EntityPath<T>>*/
{
//    @Override
//    default public void customize(QuerydslBindings bindings, EntityPath<T> entity){
////        Class<? extends T> model = entity.getType();
////        PathBuilder<T> pathBuilder = new PathBuilder<T>(model, model.getSimpleName().toLowerCase());
////        Path<T> entityPath = Expressions.path(model, model.getSimpleName().toLowerCase());
//
//        // Case insensitive queries for strings
//        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
//
////        for (Field field: model.getDeclaredFields()){
////            String name = field.getName();
////            Class<?> type = field.getType();
////
////            // default param
////            bindings.bind(Expressions.path(type, entityPath, name)).first(((path, value) -> path.eq(value)));
////
////            if (type.equals(String.class)){
////                StringPath fieldPath = Expressions.stringPath(entityPath, name);
////                bindings.bind(fieldPath).as(name+"EndsWith").first((StringPath path, String value) -> path.endsWith(value));
////            }
////
////        }
//    }
}
