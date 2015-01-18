import org.testng.annotations.Test


class DataManagerSystemTest {

    @Test
    void if_i_send_a_request_to_the_server_it_should_answer_hello_world() {
        //println 'http://localhost:7070'.toURL ( ).text
        assert 'http://localhost:4242/DataManager-0.1/api/amIAlive/yes'.toURL().text == "Hello World.PostgreSQL 9.3.4 on x86_64-apple-darwin13.1.0, compiled by Apple LLVM version 5.1 (clang-503.0.38) (based on LLVM 3.4svn), 64-bit"
    }


}


