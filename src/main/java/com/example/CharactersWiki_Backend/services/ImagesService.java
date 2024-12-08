package com.example.CharactersWiki_Backend.services;

import com.example.CharactersWiki_Backend.models.Image;
import com.example.CharactersWiki_Backend.models.dataTransferObjects.IdResponse;
import com.example.CharactersWiki_Backend.models.errors.BadRequestException;
import com.example.CharactersWiki_Backend.models.errors.NotFoundException;
import com.example.CharactersWiki_Backend.repositories.*;
import com.example.CharactersWiki_Backend.utilities.Quality;
import fi.solita.clamav.ClamAVClient;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

@Service
public class ImagesService implements IImagesService
{
    private final ImagesRepository imagesRepository;

    public ImagesService(ImagesRepository imagesRepository)
    {
        this.imagesRepository = imagesRepository;
    }

    public Image save(MultipartFile uploadedFile) throws Exception
    {
        ClamAVClient clamAVClient = new ClamAVClient("127.0.0.1", 3310);
        byte[] reply;
        try
        {
            reply = clamAVClient.scan(uploadedFile.getBytes());
        }
        catch (Exception exception)
        {
            throw new Exception("Uploaded file could not be scanned for infections.");
        }
        if (!ClamAVClient.isCleanReply(reply)) throw new BadRequestException("Uploaded file is infected.");


        final Tika tika = new Tika();
        String fileEnding = uploadedFile.getOriginalFilename() != null ? uploadedFile.getOriginalFilename().substring(uploadedFile.getOriginalFilename().lastIndexOf('.')) : "";
        String fileType ="";
        try
        {
            fileType = tika.detect(uploadedFile.getBytes());
        }
        catch (IOException exception)
        {
            throw new IOException("File type could not be detected.");
        }
        switch (fileType) {
            case "image/jpeg": if (!fileEnding.equals(".jpg") && !fileEnding.equals(".jpeg")) throw new BadRequestException("Uploaded file is not a valid image."); break;
            case "image/png": if (!fileEnding.equals(".png")) throw new BadRequestException("Uploaded file is not a valid image."); break;
            case "image/gif": if (!fileEnding.equals(".gif")) throw new BadRequestException("Uploaded file is not a valid image."); break;
            default: throw new BadRequestException("Uploaded file is not an image.");
        }


        Path imagesDirectory = Paths.get(System.getProperty("user.dir"), "images");
        if (!Files.exists(imagesDirectory)) Files.createDirectories(imagesDirectory);

        UUID uuidFileFullQuality = UUID.randomUUID();
        File fileFullQuality = new File(imagesDirectory.toString(), uuidFileFullQuality + fileEnding);
        uploadedFile.transferTo(fileFullQuality);

        UUID uuidFilePreviewQuality = UUID.randomUUID();
        File filePreviewQuality = new File(imagesDirectory.toString(), uuidFilePreviewQuality + fileEnding);
        Thumbnails
            .of(fileFullQuality)
            .size(640, 480)
            .toFile(filePreviewQuality);


        try {
            setFilePermissions(fileFullQuality);
            setFilePermissions(filePreviewQuality);
        }
        catch (IOException exception)
        {
            throw new Exception("File permissions could not be set.");
        }


        BufferedImage bufferedImageFullQuality = ImageIO.read(fileFullQuality);
        BufferedImage bufferedImagePreviewQuality = ImageIO.read(filePreviewQuality);
        Image image = new Image(uuidFileFullQuality, uuidFilePreviewQuality, fileEnding, uploadedFile.getOriginalFilename(), bufferedImageFullQuality.getWidth(), bufferedImageFullQuality.getHeight(), bufferedImagePreviewQuality.getWidth(), bufferedImagePreviewQuality.getHeight());
        imagesRepository.save(image);
        imagesRepository.flush();

        return image;
    }

    public Optional<byte[]> get(int id, Quality quality) throws Exception
    {
        Image image = imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Image with id " + id + " not found."));

        Path imagesDirectory = Paths.get(System.getProperty("user.dir"), "images");
        if (!Files.exists(imagesDirectory)) throw new NotFoundException("Image directory does not exist.");

        File file = new File(
            imagesDirectory.toString(),
            quality == Quality.Full ? image.getFileNameFullQuality() : image.getFileNamePreviewQuality()
        );
        if (!file.exists()) throw new NotFoundException("Image with id " + id + "in " + quality + " quality not found.");
        return Optional.of(Files.readAllBytes(file.toPath()));
    }

    public void delete(int id) throws Exception
    {
        Image image = imagesRepository.findById(id).orElseThrow(() -> new NotFoundException("Image with id " + id + " not found."));

        Path imagesDirectory = Paths.get(System.getProperty("user.dir"), "images");
        if (!Files.exists(imagesDirectory)) throw new NotFoundException("Image directory does not exist.");

        File fileFullQuality = new File(imagesDirectory.toString(), image.getFileNameFullQuality());
        if (!fileFullQuality.exists()) throw new NotFoundException("Image with id " + id + "in full quality not found.");

        File filePreviewQuality = new File(imagesDirectory.toString(), image.getFileNamePreviewQuality());
        if (!filePreviewQuality.exists()) throw new NotFoundException("Image with id " + id + "in preview quality not found.");

        if(!fileFullQuality.delete()) throw new Exception("Image with id " + id + "in full quality could not be deleted.");
        if(!filePreviewQuality.delete()) throw new Exception("Image with id " + id + "in preview quality could not be deleted.");

        imagesRepository.delete(image);
    }

    private void setFilePermissions(File file) throws IOException
    {
        Set<PosixFilePermission> permissions = new HashSet<>(
            Arrays.asList(
                PosixFilePermission.OWNER_READ,
                PosixFilePermission.OWNER_WRITE,
                PosixFilePermission.GROUP_READ,
                PosixFilePermission.OTHERS_READ
            )
        );
        Files.setPosixFilePermissions(file.toPath(), permissions);
    }
}
