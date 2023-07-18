/*    */
package com.solusinegeri.web.helper.reactive;
/*    */
/*    */

import com.solusinegeri.common.function.ThrowableFunction;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;

/*    */
/*    */
/*    */
/*    */
/*    */ public class FileHelper
    /*    */ {

  /*    */
  public static ResponseEntity<Resource> attachment(byte[] file, String fileName) {
    /* 30 */
    return ok(file, createAttachmentContentDisposition(fileName), determineMediaType(fileName));
    /*    */
  }

  /*    */
  /*    */
  public static ResponseEntity<Resource> inline(byte[] file, String fileName) {
    /* 34 */
    return ok(file, createInlineContentDisposition(fileName), determineMediaType(fileName));
    /*    */
  }

  /*    */
  /*    */
  public static ResponseEntity<Resource> ok(byte[] file, ContentDisposition contentDisposition, MediaType mediaType) {
    /* 38 */
    HttpHeaders headers = new HttpHeaders();
    /* 39 */
    headers.setContentDisposition(contentDisposition);
    /* 40 */
    headers.setContentType(mediaType);
    /* 41 */
    return ((ResponseEntity.BodyBuilder) ResponseEntity.ok()
        /* 42 */.headers(headers))
        /* 43 */.body(new ByteArrayResource(file));
    /*    */
  }

  /*    */
  /*    */
  public static Mono<byte[]> toByteArray(FilePart filePart) {
    /* 47 */
    return DataBufferUtils.join(filePart.content())
        /* 48 */.map(DataBuffer::asInputStream)
        /* 49 */.map(ThrowableFunction.apply(InputStream::readAllBytes));
    /*    */
  }

  /*    */
  /*    */
  public static Mono<File> toFile(FilePart filePart) {
    /* 53 */
    return Mono.fromSupplier(() -> {
          try {
            return createTempFile(filePart);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        })
        /* 54 */.flatMap(path -> {
          /*    */
          Objects.requireNonNull(path);
          return DataBufferUtils.write(filePart.content(), path, new java.nio.file.OpenOption[0])
              .then(Mono.fromSupplier(path::toFile));
          /* 56 */
        })
        .doOnNext(File::deleteOnExit);
    /*    */
  }

  /*    */
  /*    */
  private static ContentDisposition createAttachmentContentDisposition(String fileName) {
    /* 60 */
    return ContentDisposition.attachment()
        /* 61 */.filename(fileName)
        /* 62 */.build();
    /*    */
  }

  /*    */
  /*    */
  private static ContentDisposition createInlineContentDisposition(String fileName) {
    /* 66 */
    return ContentDisposition.inline()
        /* 67 */.filename(fileName)
        /* 68 */.build();
    /*    */
  }

  /*    */
  /*    */
  private static Path createTempFile(FilePart filePart) throws IOException {
    /*    */
    try {
      /* 73 */
      return Files.createTempFile(null, filePart.filename(), (FileAttribute<?>[]) new FileAttribute[0]);
      /*    */
    } catch (Throwable $ex) {
      /*    */
      throw $ex;
      /*    */
    }
  }

  private static MediaType determineMediaType(String fileName) {
    MediaType mediaType;
    /* 77 */
    String fileExtension = FilenameUtils.getExtension(fileName);
    /*    */
    /* 79 */
    if (StringUtils.equals(fileExtension, "gif")) {
      /* 80 */
      mediaType = MediaType.IMAGE_GIF;
      /* 81 */
    } else if (StringUtils.equalsAny(fileExtension, new CharSequence[]{"jpg", "jpeg"})) {
      /* 82 */
      mediaType = MediaType.IMAGE_JPEG;
      /* 83 */
    } else if (StringUtils.equals(fileExtension, "png")) {
      /* 84 */
      mediaType = MediaType.IMAGE_PNG;
      /* 85 */
    } else if (StringUtils.equals(fileExtension, "pdf")) {
      /* 86 */
      mediaType = MediaType.APPLICATION_PDF;
      /*    */
    } else {
      /* 88 */
      mediaType = MediaType.APPLICATION_OCTET_STREAM;
      /*    */
    }
    /* 90 */
    return mediaType;
  }
  /*    */
  /*    */
}


/* Location:              /home/paknar/proj/abinarystar/spring/abinarystar-spring-web/0.5.13/abinarystar-spring-web-0.5.13.jar!/com/abinarystar/spring/web/helper/reactive/FileHelper.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */