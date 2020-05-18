package ru.somarov.auth_service.backend.db.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table
data class Account(
        @Id
        //TODO: так как hibernate-а (имплементации jpa) нет, то используется spring data r2dbc
        // В этой библиотеке при вызове метода save в реактивном репозитории проверяется новизна
        // сохраняемой сущности. Из-за того, что мы передаем uuid уже на этапе вызова save,
        // data r2dbc смотрит на заполненный id и считает, что такая сущность уже есть. Поэтому она
        // вызывает не persist метод, который просто инсертит, а merge метод, который делает update.
        // Из-за того, что записи с таким id еще нет вылезает ошибка.
        // В hibernate это работает по-другому. Если id заполнен, то вызывается сначала select, а потом
        // insert или update в зависимости от наличия
        // Если же id не заполнен, то hibernate/jpa даже несмотря на автоматическое заполнение им uuid-а
        // вызовет только insert.
        // Необходимо имплементировать новый метод в репозитории для insert-а.
        var id: UUID? = null,
        var email: String,
        var password: String = "",
        var accountNonExpired: Boolean? = false,
        var accountNonLocked: Boolean? = false,
        var credentialsNonExpired: Boolean? = false,
        var enabled: Boolean? = false
)

