package com.soldev

import org.junit.Ignore

/**
 * Created by kjansen on 27/09/14.
 */
class GetFilteredPowerUsage {

    @Ignore
    void if_i_send_a_request_to_the_server_with_getDay_it_should_answer_the_powerUsageOfToday() {
        def url = "http://odin.nl.cx:9080/smartMeter/getDay?day=27&month=9&year=2014".toURL()

        // Simple Integer enhancement to make
        // 10.seconds be 10 * 1000 ms.
        Integer.metaClass.getSeconds = { ->
            delegate * 1000
        }

        url.newReader(connectTimeout: 10.seconds, useCaches: true).withReader { reader ->
            println(reader.readLine())
            assert reader.readLine() == "<html><head><title>SmartMeter</title></head><body>PostgreSQL 9.2.8 on x86_64-redhat-linux-gnu, compiled by gcc (GCC) 4.8.2 20131212 (Red Hat 4.8.2-7), 64-bit</body></html>"
        }
    }

}
