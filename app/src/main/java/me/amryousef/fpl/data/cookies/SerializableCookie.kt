package me.amryousef.fpl.data.cookies

import android.util.Log
import okhttp3.Cookie
import okio.ByteString.Companion.decodeHex
import java.io.*

class SerializableCookie : Serializable {
    @Transient
    private var cookie: Cookie? = null
    fun encode(cookie: Cookie?): String? {
        this.cookie = cookie
        val byteArrayOutputStream = ByteArrayOutputStream()
        var objectOutputStream: ObjectOutputStream? = null
        try {
            objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(this)
        } catch (e: IOException) {
            Log.d(TAG, "IOException in encodeCookie", e)
            return null
        } finally {
            if (objectOutputStream != null) {
                try {
                    // Closing a ByteArrayOutputStream has no effect, it can be used later (and is used in the return statement)
                    objectOutputStream.close()
                } catch (e: IOException) {
                    Log.d(TAG, "Stream not closed in encodeCookie", e)
                }
            }
        }

        return byteArrayOutputStream.toByteArray().toHex()
    }

    fun decode(encodedCookie: String): Cookie? {
        val bytes = encodedCookie.decodeHex().toByteArray()
        val byteArrayInputStream = ByteArrayInputStream(
            bytes
        )
        var cookie: Cookie? = null
        var objectInputStream: ObjectInputStream? = null
        try {
            objectInputStream = ObjectInputStream(byteArrayInputStream)
            cookie = (objectInputStream.readObject() as SerializableCookie).cookie
        } catch (e: IOException) {
            Log.d(TAG, "IOException in decodeCookie", e)
        } catch (e: ClassNotFoundException) {
            Log.d(TAG, "ClassNotFoundException in decodeCookie", e)
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close()
                } catch (e: IOException) {
                    Log.d(TAG, "Stream not closed in decodeCookie", e)
                }
            }
        }
        return cookie
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookie!!.name())
        out.writeObject(cookie!!.value())
        out.writeLong(if (cookie!!.persistent()) cookie!!.expiresAt() else NON_VALID_EXPIRES_AT)
        out.writeObject(cookie!!.domain())
        out.writeObject(cookie!!.path())
        out.writeBoolean(cookie!!.secure())
        out.writeBoolean(cookie!!.httpOnly())
        out.writeBoolean(cookie!!.hostOnly())
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        val builder = Cookie.Builder()
        builder.name(`in`.readObject() as String)
        builder.value(`in`.readObject() as String)
        val expiresAt = `in`.readLong()
        if (expiresAt != NON_VALID_EXPIRES_AT) {
            builder.expiresAt(expiresAt)
        }
        val domain = `in`.readObject() as String
        builder.domain(domain)
        builder.path(`in`.readObject() as String)
        if (`in`.readBoolean()) builder.secure()
        if (`in`.readBoolean()) builder.httpOnly()
        if (`in`.readBoolean()) builder.hostOnlyDomain(domain)
        cookie = builder.build()
    }

    companion object {
        private val TAG = SerializableCookie::class.java.simpleName
        private const val serialVersionUID = -8594045714036645534L

        /**
         * Using some super basic byte array &lt;-&gt; hex conversions so we don't
         * have to rely on any large Base64 libraries. Can be overridden if you
         * like!
         *
         * @param bytes byte array to be converted
         * @return string containing hex values
         */
        private fun ByteArray.toHex(): String =
            joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

        private const val NON_VALID_EXPIRES_AT = -1L
    }
}