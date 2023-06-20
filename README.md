# ORM-JPA

### Task

#### Recommended Timeline

The recommended timeline for the whole module is 2 weeks.

#### Business requirements

- Adjust your solution prepared in **Spring core** module to get rid of in-memory data source and use real DB.
- Add support of [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) operations for Tag (similar to
  Instagram hashtags) as well.
- Support retrieval of author by news id.
- Support retrieval of tags by news id.
- Support retrieval of news by tag names, tag ids, author name, title, content (all params are optional and can be used
  in conjunction).

#### Prerequisites

Your **Spring core** solution. Do not delete or move basic interfaces for `repository`, `service`
and `controller` layers:

- `com.mjc.school.repository.model.BaseEntity`
- `com.mjc.school.repository.BaseRepository`
- `com.mjc.school.service.BaseService`
- `com.mjc.school.controller.BaseController`

Use `org.springframework.boot:spring-boot-starter-data-jpa` dependency for
your solution, but with only one restriction: **it's prohibited to use CRUD repositories**, because the main goal of
this module is to get familiar with JPA/ORM:

- Criteria API
- JPQL
- Native queries

`org.springframework.boot:spring-boot-starter-data-jpa` dependency is provided to simplify DB related beans
configuration.

#### DataSource

Domain objects are represented by the following diagram

![](./media/source_model.png "source_model")

and have the following requirements:

- [x] All fields for News (except `authorId`) and Author are required.
- [x] News _title_ field should have length from 5 to 30.
- [x] News _content_ field should have length from 5 to 255.
- [x] News and Author _createdDate_, _lastUpdatedDate_ fields should be
  in [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) format. Example: 2018-08-29T06:12:15.156. More discussion
  here: [stackoverflow: how to get iso 8601](https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute)
  .

> Instead of explicitly set values to `createdDate`, `lastUpdatedDate` fields, you should pay attention to already existing audit
> mechanism provided by Spring and JPA).

- [x] News _authorId_ field should be mapped to the author datasource.
- [x] Author _name_ field should have length from 3 to 15.
- [x] Tag _name_ field should have length from 3 to 15.
- [x] Tag and News should have many-to-many relationship.

#### Operations

The system should expose CRUD operations for News, Author and Tag from the __main__ module in the root project:

- [x] Create News - fill only title, content, authorId, tag ids (optional) and return created news.
- [x] Create Author - fill only name and return created author.
- [x] Create Tag - fill only name and return created tag.
- [x] Get All News – return list of news.
- [x] Get All Authors – return list of authors.
- [x] Get All Tags – return list of tags.
- [x] Get News by id – return news by provided id.
- [x] Get Author by id – return author by provided id.
- [x] Get Tag by id – return tag by provided id.
- [x] Update News – update only title, content, authorId, tag ids (optional) by provided news id and return updated
  news.
- [x] Update Author – update only name by provided author id and return updated author.
- [x] Update Tag – update only name by provided tag id and return updated tag.
- [x] Delete News – delete news by provided news id and return boolean value.
- [x] Delete Author – delete author by provided author id and return boolean value. When deleting author you could
  choose 2 options:
  - set `authorId` field for corresponding news to `null`.
  - remove corresponding news.

  Instead of explicitly maintaining data consistency (deleting related entities together with the parent one manually),
  you should pay attention to the correct description of relationships between entities using JPA
  annotations: `@OneToOne`, `@OneToMany`, `@ManyToMany` or use foreign key constraints in sql scripts.
- [x] Delete Tag – delete tag by provided tag id and return boolean value.
- [x] Get Author by news id – return author by provided news id.
- [x] Get Tags by news id – return tags by provided news id.
- [x] Get News by tag names, tag ids, author name, title, content (all params are optional and can be used in
  conjunction) – return news by provided params.

As well as in the **Spring Core** module all returned and received data should be
like [DTO](https://en.wikipedia.org/wiki/Data_transfer_object) type.

> It would be interesting to read about `LazyInitializationException` and reasons to use DTO pattern along with JPA.

The mapping between the `dto` and the `model (domain object)` should be done at the service layer using any library. For
example: [Mapstruct](https://mapstruct.org/), [Modelmapper](http://modelmapper.org/).

#### Validation

Validate all the input according to the rules described in [DataSource](#datasource). It can be done by directly
implementing all validations in business logic code or declaratively, e.g. via custom annotations.
> To support your custom annotations and perform validation outside of business logic code you can use
> e.g. [Aspects][1].

#### Testing

- [x] Cover service layer with JUnit tests.

#### General requirements:

1. Code should be clean and should not contain any “developer-purpose” constructions.
2. App should be designed and written with respect to OOD and SOLID principles.
3. Clear layered structure should be used with responsibilities of each application layer defined.
4. All business logic should be written in the module-service: mapping `model` to `dto` and vice versa, validation, etc.
5. Module-web and module-service should accept and return `dto` objects.
6. Module-repository should accept and return `model` objects.
7. Convenient error/exception should be implemented: all errors should be meaningful. Errors should
   contain `errorMessage` and `errorCode`, where `errorCode` is your custom code.
8. Application should be tested and pass all tests suites.
9. Change archunit-junit dependency to archunit-junit5:  `testImplementation "com.tngtech.archunit:archunit-junit5:1.0.0"`
10. Use javax `implementation "org.springframework.boot:spring-boot-starter-data-jpa:2.7.7"`. Exactly `2.7.7` version, because older versions is jakarta.

#### Application requirements:

1. Java 17 should be used.
2. Gradle. Multi-module project. Spring Boot.
3. Application packages root: `com.mjc.school`.
4. Java Code Convention is mandatory.

#### Our solution review:
If you have finished task and would like to see the original solution of it written by our experts, write in #stage-3 channel about it. Access will be provided.

[1]: https://docs.spring.io/spring-framework/docs/5.3.x/reference/html/core.html#aop