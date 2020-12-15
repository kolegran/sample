import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import java.nio.file.Files
import java.nio.file.Path

class DockerImageBuildTask extends DefaultTask {

    private static final String DOCKER_REPOSITORY = "kolegran"
    private static final String JAR_DIR = "libs"
    private static final String DOCKER_BUILD_SCRIPT = "build_docker_image.sh"
    private static final String DOCKERFILE = "Dockerfile"

    @TaskAction
    void run() {
        try {
            modifyDockerBuildScript(DOCKER_BUILD_SCRIPT)
            modifyDockerfile(DOCKERFILE)

            final Path buildDirPath = getProject().getBuildDir().toPath()
            File file = new File(buildDirPath.toString() + "/" + DOCKER_BUILD_SCRIPT)
            String text = file.getText()

            final StringBuilder sout = new StringBuilder()
            final StringBuilder serr = new StringBuilder()
            final Process process = text.execute()
            process.consumeProcessOutput(sout, serr)
            process.waitForOrKill(1000)
            println "out> $sout err> $serr"

        } catch (IOException e) {
            throw new HandleFileException(e)
        }
    }

    private String modifyDockerBuildScript(String resourceName) {
        final InputStream inputStream = getClass().getResourceAsStream(resourceName)
        final String replaceDockerRepository = inputStream.getText().replace("{{DOCKER_REPOSITORY}}", DOCKER_REPOSITORY)
        final String replaceUploadProjectName = replaceDockerRepository.replace("{{UPLOAD_PROJECT_NAME}}", getProject().getName())
        createModifiedFile(resourceName, replaceUploadProjectName.getBytes())
    }

    private void modifyDockerfile(String resourceName) {
        final InputStream inputStream = getClass().getResourceAsStream(resourceName)
        final String replacePathToJar = inputStream.getText().replace("\${PATH_TO_JAR}", JAR_DIR)

        final Path buildDirPath = getProject().getBuildDir().toPath()
        final String pathToJar = buildDirPath.toString() + "/" + JAR_DIR
        final File[] files = new File(pathToJar).listFiles()
        final String jarName = files[0].getName()
        final String replaceJarName = replacePathToJar.replace("\${JAR}", jarName)
        createModifiedFile(resourceName, replaceJarName.getBytes())
    }

    private void createModifiedFile(String resourceName, byte[] content) {
        final Path buildDirPath = getProject().getBuildDir().toPath()
        Files.write(buildDirPath.resolve(resourceName), content)
    }

    private static final class HandleFileException extends RuntimeException {

        HandleFileException(Exception exception) {
            super(exception)
        }
    }
}
