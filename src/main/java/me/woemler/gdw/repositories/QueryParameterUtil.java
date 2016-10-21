package me.woemler.gdw.repositories;

import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.rest.core.annotation.RestResource;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.woemler.gdw.models.Model;

/**
 * @author woemler
 */
public class QueryParameterUtil {

    private static final Logger logger = LoggerFactory.getLogger(QueryParameterUtil.class);

    public static final String STARTS_WITH_SUFFIX = "StartsWith";
    public static final String ENDS_WITH_SUFFIX = "EndsWith";
    public static final String LIKE_SUFFIX = "Like";
    public static final String NOT_LIKE_SUFFIX = "NotLike";

    /**
     * Inspects a {@link Model} class and returns all of the available and acceptable query parameter
     *   definitions, as a map of parameter names and {@link QuerydslParameterDescriptor} objects.
     *
     * @param model model to inspect
     * @return map of parameter names and their descriptors
     */
    public static Map<String,QuerydslParameterDescriptor> getAvailableQueryParameters(
            Class<?> model, boolean recursive)
    {
        logger.debug(String.format("Determining available query parameters for model: %s", model.getName()));

        Class<?> current = model;
        Path<?> entityPath = Expressions.path(model, model.getSimpleName());
        Map<String,QuerydslParameterDescriptor> paramMap = new HashMap<>();

        while (current.getSuperclass() != null) {
            for (Field field : current.getDeclaredFields()) {

                String fieldName = field.getName();
                Class<?> type = field.getType();
                RestResource restResource = null;

                if (field.isSynthetic()) continue;

                if (field.isAnnotationPresent(RestResource.class)){
                    restResource = field.getAnnotation(RestResource.class);
                }

                if (restResource != null && !restResource.exported()) continue;

                // String type
                if (type.equals(String.class)){
                    paramMap.put(fieldName, new QuerydslParameterDescriptor(
                            fieldName, Expressions.stringPath(entityPath, fieldName), type, Ops.EQ_IGNORE_CASE));
                    paramMap.put(fieldName + STARTS_WITH_SUFFIX, new QuerydslParameterDescriptor(
                            fieldName + STARTS_WITH_SUFFIX, Expressions.stringPath(entityPath, fieldName),
                            type, Ops.STARTS_WITH_IC));
                    paramMap.put(fieldName + ENDS_WITH_SUFFIX, new QuerydslParameterDescriptor(
                            fieldName + ENDS_WITH_SUFFIX, Expressions.stringPath(entityPath, fieldName),
                            type, Ops.ENDS_WITH_IC));
                    paramMap.put(fieldName + LIKE_SUFFIX, new QuerydslParameterDescriptor(
                            fieldName + LIKE_SUFFIX, Expressions.stringPath(entityPath, fieldName),
                            type, Ops.LIKE_IC));
                }
                // Numeric type
//                else if (type.isAssignableFrom(Number.class)){
//                    paramMap.put(fieldName, new QuerydslParameterDescriptor(
//                            fieldName, Expressions.numberPath(type, entityPath, fieldName), type, Ops.EQ));
//                }
                else {
                    paramMap.put(fieldName, new QuerydslParameterDescriptor(
                            fieldName, Expressions.path(type, entityPath, fieldName), type, Ops.EQ));
                }

//                if (Collection.class.isAssignableFrom(field.getType())) {
//                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
//                    type = (Class<?>) parameterizedType.getActualTypeArguments()[0];
//                }
//
//                if (!field.isAnnotationPresent(Ignored.class)) {
//                    QueryParameterDescriptor descriptor = new QueryParameterDescriptor(fieldName, fieldName,
//                            type, Evaluation.EQUALS, false, true);
//                    paramMap.put(fieldName, descriptor);
//                    logger.debug(String.format("Adding default query parameter: %s = %s",
//                            fieldName, descriptor.toString()));
//                }
//                if (field.isAnnotationPresent(xxx.class)) {
//                    if (!recursive)
//                        continue;
//                    xxx foreignKey = field.getAnnotation(xxx.class);
//                    logger.debug(String.format("Adding foreign key parameters for model: %s",
//                            foreignKey.model().getName()));
//                    String relField = !"".equals(foreignKey.rel()) ? foreignKey.rel() : fieldName;
//                    Map<String, QueryParameterDescriptor> foreignModelMap =
//                            getAvailableQueryParameters(foreignKey.model(), false);
//                    for (QueryParameterDescriptor descriptor : foreignModelMap.values()) {
//                        String newParamName = relField + "." + descriptor.getParamName();
//                        descriptor.setParamName(newParamName);
//                        paramMap.put(newParamName, descriptor);
//                        logger.debug(String.format("Adding foreign key parameter: %s = %s",
//                                newParamName, descriptor.toString()));
//                    }
//                }
//                if (field.isAnnotationPresent(Aliases.class)) {
//                    Aliases aliases = field.getAnnotation(Aliases.class);
//                    logger.debug(String.format("Adding parameter aliases for field: %s", field.getName()));
//                    for (Alias alias : aliases.value()) {
//                        QueryParameterDescriptor descriptor = getDescriptorFromAlias(alias, type, fieldName);
//                        if (descriptor != null) {
//                            paramMap.put(alias.value(), descriptor);
//                            logger.debug(String.format("Adding alias parameter: %s = %s", alias.value(), descriptor));
//                        }
//                    }
//                } else if (field.isAnnotationPresent(Alias.class)) {
//                    Alias alias = field.getAnnotation(Alias.class);
//                    if (alias.exposed()) {
//                        QueryParameterDescriptor descriptor = getDescriptorFromAlias(alias, type, fieldName);
//                        if (descriptor != null) {
//                            paramMap.put(alias.value(), descriptor);
//                            logger.debug(String.format("Adding alias parameter: %s = %s", alias.value(), descriptor));
//                        }
//                    }
//                }
            }
            current = current.getSuperclass();
        }
        logger.debug(String.format("Found %d query parameters for model: %s", paramMap.size(), model.getName()));
        return paramMap;
    }

    /**
     * Inspects a {@link Model} class and returns all of the available and acceptable query parameter
     *   definitions, as a map of parameter names and {@link QuerydslParameterDescriptor} objects.
     *
     * @param model model to inspect
     * @return map of parameter names and their descriptors
     */
    public static Map<String,QuerydslParameterDescriptor> getAvailableQueryParameters(Class<?> model) {
        return getAvailableQueryParameters(model, true);
    }

    /**
     * Converts an object into the appropriate type defined by the model field being queried.
     *
     * @param param
     * @param type
     * @return
     */
    private static Object convertParameter(Object param, Class<?> type, ConversionService conversionService){
        if (conversionService.canConvert(param.getClass(), type)){
            try {
                return conversionService.convert(param, type);
            } catch (ConversionFailedException e){
                e.printStackTrace();
                throw new RuntimeException("Unable to convert parameter string to " + type.getName());
            }
        } else {
            return param;
        }
    }

    /**
     * {@link QueryParameterUtil#convertParameter(Object, Class, ConversionService)}
     */
    private static Object convertParameter(Object param, Class<?> type){
        ConversionService conversionService = new DefaultConversionService();
        return convertParameter(param, type, conversionService);
    }

    /**
     * Converts an array of objects into the appropriate type defined by the model field being queried
     *
     * @param params
     * @param type
     * @return
     */
    private static List<Object> convertParameterArray(Object[] params, Class<?> type){
        List<Object> objects = new ArrayList<>();
        for (Object param: params){
            objects.add(convertParameter(param, type));
        }
        return objects;
    }

}
