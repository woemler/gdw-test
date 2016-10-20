package me.woemler.gdw;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author woemler
 */
public class DynamicParameterPredicateBuilder extends QuerydslPredicateBuilder {

	public DynamicParameterPredicateBuilder(
			ConversionService conversionService,
			EntityPathResolver resolver) {
		super(conversionService, resolver);
	}

	@Override
	public Predicate getPredicate(TypeInformation<?> type, MultiValueMap<String, String> parameters,
			QuerydslBindings bindings) {
		
		Assert.notNull(bindings, "Context must not be null!");
		BooleanBuilder builder = new BooleanBuilder();

		if (parameters.isEmpty()) {
			return builder.getValue();
		}
		
		Class<?> model = type.getType();
		Path<?> entityPath = Expressions.path(model, model.getSimpleName());

		for (Map.Entry<String, List<String>> param : parameters.entrySet()) {

			if (param.getValue().size() == 1 && 
					(param.getValue().get(0) == null || param.getValue().get(0).equals(""))) {
				continue;
			}

			String path = param.getKey();
			// TODO: get available query params
			// TODO: does the path match any of the params with prefix?
			// TODO: If yes, add predicate, if no pass
			
			
			if (!bindings.isPathAvailable(path, type)) {
				continue;
			}

			PropertyPath propertyPath = bindings.getPropertyPath(path, type);

			if (propertyPath == null) {
				continue;
			}

			Collection<Object> value = convertToPropertyPathSpecificType(entry.getValue(), propertyPath);
			Predicate predicate = invokeBinding(propertyPath, bindings, value);

			if (predicate != null) {
				builder.and(predicate);
			}
		}

		return builder.getValue();
	}

	private static boolean isSingleElementCollectionWithoutText(List<String> source) {
		return source.size() == 1 && !StringUtils.hasText(source.get(0));
	}
	
}
