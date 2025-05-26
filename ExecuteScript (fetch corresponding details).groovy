import groovy.json.*

def flowFile = session.get()
if (!flowFile) return

flowFile = session.write(flowFile, { inputStream, outputStream ->
    def json = new JsonSlurper().parse(inputStream)
    def result = json.find { it.class == "snickers" }
    outputStream.write(JsonOutput.toJson(result ?: [:]).getBytes("UTF-8"))
} as StreamCallback)

session.transfer(flowFile, REL_SUCCESS)