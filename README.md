# jpa-audit-light
Auditing JPA entities using interceptors, audit tables and triggers

There are some business applications where data modification must be traced for auditing.
The is no standard solution for this requirement. For example using the Hibernate Envers framework (https://hibernate.org/orm/envers/) your mappend entities can be versioned and queried, but it does not provide info about the user who made the modification. Moreover using any framework you have to introduce additional dependency and overhead into your application.

The following sample project shows an alternative for auditing of JPA domain model where you can trace not only the data changes but it extends with the executor user too.

Let's say you have an authenticated user and this security info is wrapped into a UserContext type.
When the user calls an API - for example
```
FooBusiness#create(String fooName, UserContext user)
```
your API should contain this extra parameter. Yes, it is extra, because we want to audit this info too. Of course if you use Spring Security this information is already stored in the executor thread and can be read any time within the same thread, but in our case we use Wildfly JEE container without any Spring support.

Next we have to define an interceptor that can be applied for any business service API. Its main responsibility is to store this security info into the thread context before the business execution, and clean this after the business API is returned.

The user context is used by a JPA listener when lifecycle events occur like PrePersist or PreUpdate. Moreover we can set the creation or modification time for the given entity instance.

If we want to trace all modifications of the database records we can use functions and triggers provided by the database engine (see example in the sql folder).

In our sample we define a Foo table and its audit counterpart in a PostgreSQL database.
```
jpa-sample=# select * from foo;
  id  |                 name                 |      creation_time      | modification_time | created_by | modified_by 
------+--------------------------------------+-------------------------+-------------------+------------+-------------
 1000 | 152e1d0f-5e2d-4c45-820f-d3240abe5205 | 2018-12-26 20:22:30.248 |                   |          1 |            
 1001 | 1f825490-fd87-4cb2-b64a-4edcbf1e6c61 | 2018-12-26 20:34:53.049 |                   |          1 |            
 1002 | 4229965f-13e6-4410-8aa5-7c1485c3d93b | 2018-12-26 20:37:54.297 |                   |          1 |            
 1003 | 6ef65f53-5a7d-4348-89b0-a4d76f9efd41 | 2018-12-26 21:21:25.626 |                   |          1 |            
 1004 | 378e8fae-a3a1-4ed9-8895-eebc39a51d4a | 2018-12-26 21:22:20.989 |                   |          1 |            
(5 rows)

jpa-sample=# select * from a_foo;
  id  |                 name                 |      creation_time      | modification_time | created_by | modified_by | operation 
------+--------------------------------------+-------------------------+-------------------+------------+-------------+-----------
 1000 | 152e1d0f-5e2d-4c45-820f-d3240abe5205 | 2018-12-26 20:22:30.248 |                   |          1 |             | INSERT
 1001 | 1f825490-fd87-4cb2-b64a-4edcbf1e6c61 | 2018-12-26 20:34:53.049 |                   |          1 |             | INSERT
 1002 | 4229965f-13e6-4410-8aa5-7c1485c3d93b | 2018-12-26 20:37:54.297 |                   |          1 |             | INSERT
 1003 | 6ef65f53-5a7d-4348-89b0-a4d76f9efd41 | 2018-12-26 21:21:25.626 |                   |          1 |             | INSERT
 1004 | 378e8fae-a3a1-4ed9-8895-eebc39a51d4a | 2018-12-26 21:22:20.989 |                   |          1 |             | INSERT
(5 rows)
```
As you can see in this solution we can record time, user and operation type information.

If you primary goal is only recording the last audit events, then you can get rid of SQL functions and triggers, so audit table is not neccessary.
