package com.toplivo.home_service.service

import com.ninjasquad.springmockk.MockkBean
import com.toplivo.home_service.dto.Room
import com.toplivo.home_service.dto.request.RoomRequest
import com.toplivo.home_service.entity.RoomEntity
import com.toplivo.home_service.exception.ApiError
import com.toplivo.home_service.repository.HomeRepository
import com.toplivo.home_service.repository.RoomRepository
import com.toplivo.home_service.service.container.PostgresTestContainer
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@Import(RoomService::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
    classes = [],
    initializers = [PostgresTestContainer.Initializer::class]
)
@ExtendWith(SpringExtension::class)
class RoomServiceTests {

    @Autowired
    private lateinit var roomRepository: RoomRepository


    @Autowired
    private lateinit var roomService: RoomService

    @MockkBean
    private lateinit var validationService: ValidationService

    @MockkBean
    private lateinit var homeRepository: HomeRepository

    @MockkBean
    private lateinit var parseService: ParseService

    @MockkBean
    private lateinit var eventService: EventService

    private val validToken = "{\"userId\":1}"

    @Nested
    inner class CreateRoom {

        @Test
        fun success() {
            val request = RoomRequest(
                name = "home"
            )


            every { homeRepository.existsById(1) }.returns(true)
            every { parseService.parseOwnerId(validToken) }.returns(1)
            every { validationService.validateByOwnerAndHomeId(1, 1) }
                .returns(true)

            val room = roomService.createRoom(token = validToken, 1, roomRequest = request)

            val expectedRoom = Room(
                id = 1,
                name = request.name
            )

            assertEquals(expectedRoom, room)
        }

        @Test
        fun `should throw an exception if user does not own home`() {
            val request = RoomRequest(
                name = "home"
            )

            every { parseService.parseOwnerId(validToken) }.returns(1)
            every { validationService.validateByOwnerAndHomeId(1, 1) }
                .returns(false)

            expectApiException(ApiError.HOME_NOT_FOUND) {
                roomService.createRoom(token = validToken, 1, roomRequest = request)
            }
        }
    }
    @Nested
    inner class UpdateRoom {
        @Test
        fun success() {
            val request = RoomRequest(
                name = "room"
            )

            val persistedRoom = roomRepository.save(
                RoomEntity(
                    name = "room",
                    homeId = 1
                )
            )
            every { validationService.validateByOwnerAndHomeId(1, persistedRoom.id) }.returns(true)
            every { parseService.parseOwnerId(validToken) }.returns(1)

            val room = roomService.updateRoom(token = validToken, id = persistedRoom.id, roomRequest = request)

            assertEquals(persistedRoom.toDto(), room)
        }

        @Test
        fun `should throw an exception if home does not exist`() {

            val homeId = 1

            val request = RoomRequest(
                name = "room"
            )

            every { parseService.parseOwnerId(validToken) }.returns(1)
//            every { validationService.validateByOwnerAndHomeId(1, homeId) }
//                .returns(false)


            assertFalse(roomRepository.existsById(1))

            expectApiException(ApiError.ROOM_NOT_FOUND) {
                roomService.updateRoom(validToken, homeId, request)
            }
        }

        @Test
        fun `should throw an exception if ownerId's does not match`() {

            val homeId = 1

            val request = RoomRequest(
                name = "room"
            )

            every { parseService.parseOwnerId(validToken) }.returns(1)
            every { validationService.validateByOwnerAndHomeId(1, homeId) }
                .returns(false)

            expectApiException(ApiError.ROOM_NOT_FOUND) {
                roomService.updateRoom(validToken, homeId, request)
            }
        }
    }

    @Nested
    inner class DeleteHome {
        @Test
        fun success() {

            val persistedRoom = roomRepository.save(
                RoomEntity(
                    name = "home",
                    homeId = 1
                )
            )

            val roomId = persistedRoom.id

            every { eventService.saveRoomEvent(roomId) }.returns(Unit)
            every { parseService.parseOwnerId(validToken) }.returns(1)
            every { validationService.validateByOwnerAndRoomId(1, roomId) }
                .returns(true)

            assertTrue(roomRepository.existsById(roomId))

            roomService.deleteRoom(validToken, roomId)

            assertFalse(roomRepository.existsById(roomId))

        }

        @Test
        fun `should throw an exception if room does not exist`() {
            val roomId = 1

            every { parseService.parseOwnerId(validToken) }.returns(1)
            every { validationService.validateByOwnerAndRoomId(1, roomId) }
                .returns(false)

            expectApiException(ApiError.ROOM_NOT_FOUND) {
                roomService.deleteRoom(validToken, roomId)
            }
        }

        @Test
        fun `should throw an exception if ownerId's does not match`() {
            val roomId = 1

            every { parseService.parseOwnerId(validToken) }.returns(1)
            every { validationService.validateByOwnerAndRoomId(1, roomId) }
                .returns(false)

            expectApiException(ApiError.HOME_NOT_FOUND) {
                roomService.deleteRoom(validToken, roomId)
            }
        }
    }
}