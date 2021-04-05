package com.tickledmedia.tmrohit.network

import okhttp3.Dns
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress


class DnsSelector(var mode: Mode) : Dns {

    enum class Mode {
        SYSTEM, IPV4_ONLY, IPV6_ONLY, IPV4_FIRST, IPV6_FIRST
    }


    override fun lookup(hostname: String): MutableList<InetAddress> {
        var addressList: MutableList<InetAddress>
        addressList = Dns.SYSTEM.lookup(hostname).toMutableList()


        when (mode) {
            Mode.IPV6_FIRST -> {
                addressList.sortByDescending {
                    Inet6Address::class.isInstance(it)
                }
            }

            Mode.IPV4_FIRST -> {
                addressList.sortByDescending {
                    Inet4Address::class.isInstance(it)
                }
            }

            Mode.IPV6_ONLY -> {
                addressList =
                    addressList.filter { Inet6Address::class.isInstance(it) }.toMutableList()
            }

            Mode.IPV4_ONLY -> {
                addressList =
                    addressList.filter { Inet4Address::class.isInstance(it) }.toMutableList()
            }
            else -> {
                addressList =
                    addressList.filter { Inet4Address::class.isInstance(it) }.toMutableList()
            }


        }

        return addressList


    }

}