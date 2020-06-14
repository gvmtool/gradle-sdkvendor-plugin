package io.sdkman.vendors

class SdkMinorRelease extends SdkmanVendorBaseTask {

    static final ANNOUNCE_ENDPOINT = "/announce/struct"

    static final RELEASE_ENDPOINT = "/release"

    String downloadUrl
    String hashtag

    SdkMinorRelease() {
        description = "Convenience task performs a Minor Release consisting of Release and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        withConnection(apiUrl, RELEASE_ENDPOINT, consumerKey, consumerToken) { conn ->
            logger.quiet("Releasing $candidate $version...")
            def releaseValues = [candidate: candidate, version: version, url: downloadUrl]
            def releaseResponse = post(conn, releaseValues)
            logger.quiet("Response: ${releaseResponse.responseCode}: ${releaseResponse.responseMessage}...")
        }

        withConnection(apiUrl, ANNOUNCE_ENDPOINT, consumerKey, consumerToken) { conn ->
            logger.quiet("Announcing for $candidate $version...")
            def announceValues = [candidate: candidate, version: version, hashtag: hashtag ?: candidate]
            def announceResponse = post(conn, announceValues)
            logger.quiet("Response: ${announceResponse.responseCode}: ${announceResponse.responseMessage}...")
        }
    }
}
