package controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification


@AutoConfigureMockMvc
@WebMvcTest
class controllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "when get items end point is called status is 200"() {
        expect: "Status is 200 and the response is 'Hello world!'"
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == "Hello world!"

    }


}
