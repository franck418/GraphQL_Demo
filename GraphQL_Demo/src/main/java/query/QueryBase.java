package query;

import graphql.GraphQL;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import simple.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static graphql.Scalars.GraphQLInt;
import static graphql.Scalars.GraphQLString;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * Author: franck
 * Company: Shenggu
 * Date: 2017/11/2.
 *
 * 演示基础的查询
 * 1 获取一个对象 ，不用任何参数
 * 2 获取一个对象列表 不用任何参数
 * 3
 *
 */
public class QueryBase {

    public static void main(String[] args) {

        User user = new User();
        user.setName("franck");
        user.setAge(20);

        User user1 = new User();
        user1.setName("tom");
        user1.setAge(19);

        List<User> userList = Arrays.asList(user,user1);//用户列表

        // 定义GraphQL类型：需要进行查询和输出的类型定义，和另外定义的Model实体类一致
        GraphQLObjectType userType = newObject()
                .name("User")// 必须要有
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("age").type(GraphQLInt))
                .build();

        // 定义给客户端的查询接口
        GraphQLObjectType queryType = newObject()
                .name("userQuery")
                .field(
                        newFieldDefinition()
                                .type(userType)
                                .name("user")
                                .staticValue(user))//设置数据，这里设置一个默认的数据
                .build();

        // 创建Schema: Schema相当于数据库
        GraphQLSchema schema = GraphQLSchema.newSchema()
                .query(queryType)
                .build();


        // 测试输出
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();
        Map<String, Object> result = graphQL.execute("{user{name,age}}").getData();// 没有参数的单条数据的查询
        System.out.println(result);

        // 输出
        // {user={name=franck, age=20}}


    }







}
