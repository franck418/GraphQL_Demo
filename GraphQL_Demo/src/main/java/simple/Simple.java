package simple;

import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * 最简单的GraphQL Demo
 * Author: franck
 * Company: Shenggu
 * Date: 2017/11/2.
 */
public class Simple {

    public static void main(String[] args) {

        User user = new User();
        user.setName("franck");
        user.setAge(20);

        // 定义GraphQL类型：需要进行查询和输出的类型定义，和另外定义的Model实体类一致
        GraphQLObjectType userType = newObject()
                .name("User")// 必须要有
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("age").type(GraphQLInt))
                .build();

        // 定义暴露给客户端的查询query api
        GraphQLObjectType queryType = newObject()
                .name("userQuery")
                .field(newFieldDefinition().type(userType).name("user").staticValue(user))
                .build();

        // 创建Schema: Schema相当于数据库
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();

        // 测试输出
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();
        Map<String, Object> result = graphQL.execute("{user{name,age}}").getData();// 执行查询
        System.out.println(result);

        // 输出
        // {user={name=franck, age=20}}
    }
}
