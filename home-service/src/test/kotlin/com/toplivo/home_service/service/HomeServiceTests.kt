package com.toplivo.home_service.service

import com.toplivo.home_service.dto.Home
import com.toplivo.home_service.dto.request.HomeRequest
import com.toplivo.home_service.entity.HomeEntity
import com.toplivo.home_service.exception.ApiError
import com.toplivo.home_service.repository.HomeRepository
import com.toplivo.home_service.service.container.PostgresTestContainer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@Import(HomeService::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(
	classes = [],
	initializers = [PostgresTestContainer.Initializer::class]
)
@ExtendWith(SpringExtension::class)
class HomeServiceTests {


	@Autowired
	private lateinit var homeRepository: HomeRepository

	@Autowired
	private lateinit var homeService: HomeService

	@MockkBean
	private lateinit var validationService: ValidationService

	@MockkBean
	private lateinit var parseService: ParseService

	@MockkBean
	private lateinit var eventService: EventService

	private val validToken = "{\"userId\":1}"


	@Nested
	inner class CreateHome {
		@Test
		fun success() {
			val request = HomeRequest(
				name = "home",
				address = "address"
			)

			every { parseService.parseOwnerId(validToken) }.returns(1)

			val home = homeService.createHome(token = validToken, homeRequest = request)

			val expectedHome = Home(
				id = 1,
				name = request.name,
				address = request.address,
				rooms = emptyList()
			)

			assertEquals(expectedHome, home)
		}

	}

	@Nested
	inner class UpdateHome {
		@Test
		fun success() {
			val request = HomeRequest(
				name = "home",
				address = "address"
			)

			val persistedHome = homeRepository.save(
				HomeEntity(
					ownerId = 1,
					name = "home",
					address = "address"
				)
			)
			every { validationService.validateByOwnerAndHomeId(1, persistedHome.id) }.returns(true)
			every { parseService.parseOwnerId(validToken) }.returns(1)


			val home = homeService.updateHome(token = validToken, id = persistedHome.id, homeRequest = request)

			assertEquals(persistedHome.toDto(), home)
		}

		@Test
		fun `should throw an exception if home does not exist`() {

			val homeId = 1

			val request = HomeRequest(
				name = "home",
				address = "address"
			)

			every { parseService.parseOwnerId(validToken) }.returns(1)
			every { validationService.validateByOwnerAndHomeId(1, homeId) }
				.returns(false)


			assertFalse(homeRepository.existsById(homeId))

			expectApiException(ApiError.HOME_NOT_FOUND) {
				homeService.updateHome(validToken, homeId, request)
			}
		}

		@Test
		fun `should throw an exception if ownerId's does not match`() {

			val homeId = 1

			val request = HomeRequest(
				name = "home",
				address = "address"
			)

			every { parseService.parseOwnerId(validToken) }.returns(1)
			every { validationService.validateByOwnerAndHomeId(1, homeId) }
				.returns(false)

			expectApiException(ApiError.HOME_NOT_FOUND) {
				homeService.updateHome(validToken, homeId, request)
			}
		}
	}

	@Nested
	inner class DeleteHome {
		@Test
		fun success() {

			val persistedHome = homeRepository.save(
				HomeEntity(
					ownerId = 1,
					name = "home",
					address = "address"
				)
			)

			val homeId = persistedHome.id

			every { eventService.saveHomeEvent(homeId) }.returns(Unit)
			every { parseService.parseOwnerId(validToken) }.returns(1)
			every { validationService.validateByOwnerAndHomeId(1, homeId) }
				.returns(true)

			assertTrue(homeRepository.existsById(homeId))

			homeService.deleteHome(validToken, homeId)

			assertFalse(homeRepository.existsById(homeId))

		}

		@Test
		fun `should throw an exception if home does not exist`() {
			val homeId = 1

			every { parseService.parseOwnerId(validToken) }.returns(1)
			every { validationService.validateByOwnerAndHomeId(1, homeId) }
				.returns(false)

			expectApiException(ApiError.HOME_NOT_FOUND) {
				homeService.deleteHome(validToken, homeId)
			}
		}

		@Test
		fun `should throw an exception if ownerId's does not match`() {
			val homeId = 1

			every { parseService.parseOwnerId(validToken) }.returns(1)
			every { validationService.validateByOwnerAndHomeId(1, homeId) }
				.returns(false)

			expectApiException(ApiError.HOME_NOT_FOUND) {
				homeService.deleteHome(validToken, homeId)
			}
		}
	}
}
