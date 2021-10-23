/**
 * You have to have a posgresql server running with the config
 * posgres://localhost:5432/imageconverter
 * username: lion
 * password: password
 * or you can use ur own posgresql config
 */


package controllers;

import com.encentral.imageconverter.api.IImage;
import com.encentral.imageconverter.model.ApiErrorReporter;
import com.encentral.imageconverter.model.Image;
import com.encentral.scaffold.commons.util.MyObjectMapper;
import io.swagger.annotations.*;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Api(value = "/image")
@Transactional
public class ImageController extends Controller {
    @Inject
    MyObjectMapper objectMapper;

    @Inject
    IImage imageApi;

    @ApiOperation(value = "Upload New Image", httpMethod = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, response = String.class, message = "Newly uploaded image inverted url"),
                    @ApiResponse(code = 400, response = String.class, message = "Empty image"),
                    @ApiResponse(code = 400, response = String.class, message = "Invalid image"),
                    @ApiResponse(code = 500, response = String.class, message = "Unexpected Error")}
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "picture",
                    value = "Admin name",
                    paramType = "form",
                    required = true,
                    dataType = "file"
            )

    })
    public Result upload() throws IOException {
        Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");

        if (picture != null ) {

            String fileName = picture.getFilename();
            String contentType = picture.getContentType();
            if(!contentType.equals( "image/jpeg") && !contentType.equals("image/png") ) {
                return badRequest(objectMapper.writeValueAsString(new ApiErrorReporter(BAD_REQUEST, "Cannot upload non image mimetype required image/jpeg or image/png")));
            }

            File file = picture.getFile();

            Optional<Image> imageOptional = imageApi.uploadImage(file, fileName);
            if(imageOptional.isEmpty()) throw new IOException("Error");
            Image image = imageOptional.get();


            return ok( objectMapper.writeValueAsString(image));

        } else {

            return notFound(objectMapper.writeValueAsString(new ApiErrorReporter(BAD_REQUEST, "Cannot upload empty picture"  )));
        }
    }



    @ApiOperation(value = "get an image url", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 303, message = "get an image url",
                    response = String.class),

            @ApiResponse(code = 400, response = ApiErrorReporter.class, message = "No user found with token"),
            @ApiResponse(code = 500, response = ApiErrorReporter.class, message = "Unexpected Error")
    })
    public Result getImage(@ApiParam(value = "id of the image") String id) throws Exception {

        Optional<Image> optionalImage = imageApi.getImage(id);
        if(optionalImage.isEmpty()) {
            return notFound(objectMapper.writeValueAsString(new ApiErrorReporter(NOT_FOUND, "Cannot find image with id " + id  )));

        }
        Image image = optionalImage.get();

        return redirect(image.getInvertedUrl());

    }

    @ApiOperation(value = "get an image url by image name", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 303, message = "get an image url by image name",
                    response = String.class),

            @ApiResponse(code = 400, response = ApiErrorReporter.class, message = "No user found with token"),
            @ApiResponse(code = 500, response = ApiErrorReporter.class, message = "Unexpected Error")
    })
    public Result getImageByName(@ApiParam(value = "name of the image") String fileName) throws Exception {

        Optional<Image> optionalImage = imageApi.getImageByName(fileName);
        if(optionalImage.isEmpty()) {
            return notFound(objectMapper.writeValueAsString(new ApiErrorReporter(NOT_FOUND, "Cannot find image with name " + fileName  )));
        }
        Image image = optionalImage.get();

        return redirect(image.getInvertedUrl());

    }


}
