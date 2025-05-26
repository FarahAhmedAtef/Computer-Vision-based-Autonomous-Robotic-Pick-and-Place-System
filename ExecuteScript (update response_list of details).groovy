import groovy.json.*

def flowFile = session.get()
if (!flowFile) return

flowFile = session.write(flowFile, { inputStream, outputStream ->
    def json = new JsonSlurper().parse(inputStream)
    def found = false
    def result = json.findAll {
        if (!found && it.class == "snickers") {
            found = true
            return false
        }
        return true
    }
    outputStream.write(JsonOutput.toJson(result).getBytes("UTF-8"))
} as StreamCallback)

session.transfer(flowFile, REL_SUCCESS)