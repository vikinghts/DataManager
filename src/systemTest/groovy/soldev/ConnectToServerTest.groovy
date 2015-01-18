package com.soldev

import org.junit.Ignore

/**
 * Created by kjansen on 28/04/14.
 */
class ConnectToServerTest {

    @Ignore
    void if_i_send_a_request_to_the_server_with_test_it_should_answer_burp() {
        def url = "http://odin.nl.cx:9080/smartMeter/test".toURL()

        // Simple Integer enhancement to make
        // 10.seconds be 10 * 1000 ms.
        Integer.metaClass.getSeconds = { ->
            delegate * 1000
        }
        /*
        // Get content of URL with parameters.
        def content = url.getText(connectTimeout: 10.seconds, readTimeout: 10.seconds,
                useCaches: true, allowUserInteraction: false,
                requestProperties: ['User-Agent': 'Groovy Sample Script'])

        assert content == "<html><head><title>SmartMeter</title></head><body>BURP</body></html>"
        */
        url.newReader(connectTimeout: 10.seconds, useCaches: true).withReader { reader ->
            assert reader.readLine() == "<html><head><title>SmartMeter</title></head><body>BURP</body></html>"
        }
    }

    @Ignore
    void if_i_send_a_request_to_the_server_with_dbversion_it_should_answer_the_version() {
        def url = "http://odin.nl.cx:9080/smartMeter/dbversion".toURL()

        // Simple Integer enhancement to make
        // 10.seconds be 10 * 1000 ms.
        Integer.metaClass.getSeconds = { ->
            delegate * 1000
        }

        url.newReader(connectTimeout: 10.seconds, useCaches: true).withReader { reader ->
            assert reader.readLine() == "<html><head><title>SmartMeter</title></head><body>PostgreSQL 9.2.8 on x86_64-redhat-linux-gnu, compiled by gcc (GCC) 4.8.2 20131212 (Red Hat 4.8.2-7), 64-bit</body></html>"
        }
    }
}
