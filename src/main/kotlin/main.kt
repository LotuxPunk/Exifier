import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Metadata
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.path
import java.awt.Color
import java.awt.Font
import java.io.File
import java.nio.file.Path
import javax.imageio.ImageIO


class Exifier : CliktCommand() {
    val input: Path by option(help="Input path").path().required()

    override fun run() {
        val inputFile = input.toFile();

         val images : List<File> = when(inputFile.isDirectory){
             true -> inputFile.listFiles{ dir, name ->  Extensions.lowerValues.any { name.lowercase().endsWith(".$it") }}.toList()
             false -> emptyList()
        }

        println("Found ${images.count()} image(s)")

        images.forEach { file ->

            println("Processing ${file.name}")
            val image = ImageIO.read(file)

            val metadata: Metadata = ImageMetadataReader.readMetadata(file)
            val imageDate = metadata
                .directories
                .first { directory -> directory.name.equals("Exif IFD0") }
                .tags.first { tag -> tag.tagName.equals("Date/Time") }
                .description

            println("Exif date : ${imageDate}")

            val g2d = image.createGraphics()
            g2d.color = Color.ORANGE
            g2d.font = Font("Arial", Font.PLAIN, 200)
            g2d.drawString(imageDate, 20, image.height - 100)
            val output = File("${file.parent}${File.separatorChar}exified_${file.name}")
            ImageIO.write(image, "png", output)

            println("Exported result ${output.name}")
        }
    }
}

fun main(args: Array<String>) = Exifier().main(args)