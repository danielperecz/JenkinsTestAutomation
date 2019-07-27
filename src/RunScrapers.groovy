import groovy.io.FileType

String scrapersPath = "/var/lib/jenkins/workspace/wq/src/scrapers_v2/scrapers"
int scrapersPathLength = scrapersPath.length()+1

def allFiles = []
def dir = new File(scrapersPath)
dir.eachFileRecurse(FileType.FILES) { file ->
    allFiles << file
}

// Recursively iterate through all nested files & directories
allFiles.each {
    String path = it

    // This is the path split up, example: [alic, alic.py]
    String[] files =  path[scrapersPathLength..-1].split("/")

    // If files is a directory
    if (files.length > 1 & files[0] != "__pycache__") {
        // Loop through files
        files.each {
            String file = it
            // If file is not a python test file, run it
            if (!file.toLowerCase().contains("test") & file.toLowerCase().contains(".py") & !file.toLowerCase().contains("__init__.py")) {
                file.execute()
            }
        }
    }
}
