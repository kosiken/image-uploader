package com.encentral.imageconverter.impl;

import static   java.nio.file.StandardCopyOption.*;
import akka.actor.*;
import com.encentral.imageconverter.api.IImage;
import com.encentral.imageconverter.model.Image;
import com.encentral.imageconverter.model.ImageMapper;
import com.encentral.scaffold.entities.JpaImage;
import com.encentral.scaffold.entities.QJpaImage;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import play.db.jpa.JPAApi;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class ImageImpl extends AbstractImpl implements IImage {
    final ActorSystem system = ActorSystem.create("helloakka");
    final List<ActorRef> actorRefs = new ArrayList<ActorRef>();
    int actorId = 0;
    private final JPAApi jpaApi;


    @Inject
    public ImageImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }


    public Optional<Image> invertImage(String url) {


        Image image = new Image();
        image.setFileName(getFileName(url));
        image.setMimeType("image/jpeg");
        image.setUrl(url);
        image.setMimeType(getMimeType(url));
        ActorRef actor = system.actorOf(Props.create(ImageActor.class, this, ++actorId), "actor" + actorId);
        actorRefs.add(actor);
        actor.tell(image, null);
        return Optional.of(image);
    }


    public Optional<Image> invertImage(Image image) {



        ActorRef actor = system.actorOf(Props.create(ImageActor.class, this, ++actorId), "actor" + actorId);
        actorRefs.add(actor);
        actor.tell(image, null);



        return Optional.of(image);
    }
    public void onReceive(Object message, ActorRef actorRef) throws Throwable {
        System.out.println(message);
        Image image = (Image) message;
        image.setUrl("http://localhost:9000/assets/uploads/images-inverted/" + image.getIdentifier() + '.' + image.getMimeType());
        actorRefs.remove(actorRef);
        EntityTransaction transaction = null;


    }

    public String getFileName(String url) {
        var n = url.split("/");
        return n[n.length - 1].split("\\.")[0];
    }

    public String getMimeType(String url) {
        try {
            var n = url.split("/");

            return n[n.length - 1].split("\\.")[1];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            try {
                return url.split("\\.")[1];
            }
            catch (ArrayIndexOutOfBoundsException e2) {
                return "jpg";
            }
        }
    }

    @Override
    public Optional<Image> uploadImage(File file, String fileName) throws IOException {

        Image image = new Image();
        image.setFileName(fileName);
        image.setMimeType(getMimeType(fileName));
        image.setIdentifier(UUID.randomUUID().toString());
        image.setDateCreated(new Date());

        File file2 = new File((System.getProperty("user.dir") + "/public/uploads/images/" + image.getIdentifier() + '.' + image.getMimeType()));
        Files.move(file.toPath(), file2.toPath(), REPLACE_EXISTING);
        image.setUrl("http://localhost:9000/assets/uploads/images/" + image.getIdentifier() + '.' + image.getMimeType());
        image.setInvertedUrl("http://localhost:9000/assets/uploads/images-inverted/" + image.getIdentifier() + '.' + image.getMimeType());
        invertImage(image);
        jpaApi.em().persist(ImageMapper.imageToJpaImage(image));


        return Optional.of(image);
    }

    @Override
    public Optional<Image> getImage(String id) throws Exception {
        Preconditions.checkNotNull(id, "url cannot be null");
        reinitialize(jpaApi.em());
        JpaImage jpaImage = null;


        QJpaImage qJpaImage = QJpaImage.jpaImage;
        jpaImage = queryFactory.selectFrom(qJpaImage)
                .where(qJpaImage.identifier.eq(id))
                .fetchOne();

        if(jpaImage == null ) return Optional.empty();

        return Optional.ofNullable(ImageMapper.jpaImageToImage(jpaImage));
    }

    @Override
    public Optional<Image> getImageByName(String fileName) throws Exception {
        Preconditions.checkNotNull(fileName, "url cannot be null");
        reinitialize(jpaApi.em());
        JpaImage jpaImage = null;


        QJpaImage qJpaImage = QJpaImage.jpaImage;
        List<JpaImage> images  = queryFactory.selectFrom(qJpaImage)
                .where(qJpaImage.fileName.eq(fileName))
                .fetch();

        if(images.isEmpty()) return Optional.empty();
        jpaImage = images.get(0);

        return Optional.of(ImageMapper.jpaImageToImage(jpaImage));

    }



}
