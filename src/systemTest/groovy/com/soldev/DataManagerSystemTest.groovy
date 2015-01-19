package com.soldev

//import org.testng.annotations.Test
import org.junit.Test;


class DataManagerSystemTest {

    @Test
    void if_i_send_a_request_to_the_server_it_should_answer_hello_world() {
        String SYSTEM_TEST_HOST = System.getProperty("SYSTEM_TEST_HOST");
        assert "http://${SYSTEM_TEST_HOST}:4242/DataManager-0.1/api/amIAlive/yes".toURL().text == "DataManger Is ALIVE : yes"
    }
}


