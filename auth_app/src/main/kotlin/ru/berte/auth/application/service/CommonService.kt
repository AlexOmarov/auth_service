package ru.berte.auth.application.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.springframework.stereotype.Service
import ru.berte.auth.presentation.web.dto.Actor

@Service
class CommonService {
    fun getActors(): Flow<Actor> {
        return emptyFlow()
    }
}
