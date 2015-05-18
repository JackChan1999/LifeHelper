package com.example;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    public static void main(String[] args) {
        try {
            generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generate() throws Exception {
        Schema schema = new Schema(1, "com.qz.lifehelper.dao");

        Entity user = schema.addEntity("User");
        user.addIdProperty().getProperty();
        user.addDateProperty("createdAt");
        user.addStringProperty("name").unique();
        user.addStringProperty("password");

        Entity image = schema.addEntity("Image");
        image.addIdProperty();
        image.addDateProperty("createdAt");
        image.addStringProperty("imageSrc");

        Entity p2p = schema.addEntity("P2P");
        p2p.addIdProperty();
        p2p.addDateProperty("createdAt");
        p2p.addStringProperty("title");
        p2p.addStringProperty("price");
        p2p.addStringProperty("detail");
        p2p.addStringProperty("add");
        p2p.addStringProperty("tel");
        p2p.addStringProperty("category");
        Property P2PUserProperty = p2p.addLongProperty("userId").getProperty();
        p2p.addToOne(user, P2PUserProperty, "user");
        Property P2PImageProperty = p2p.addLongProperty("imageId").getProperty();
        p2p.addToOne(image, P2PImageProperty, "image");

        Entity poi = schema.addEntity("POI");
        poi.addIdProperty();
        poi.addDateProperty("createdAt");
        poi.addStringProperty("title");
        poi.addStringProperty("detail");
        poi.addStringProperty("tel");
        poi.addStringProperty("add");
        poi.addStringProperty("category");
        poi.addStringProperty("city");
        Property POIUserProperty = poi.addLongProperty("userId").getProperty();
        poi.addToOne(user, POIUserProperty);
        Property POIImageProperty = poi.addLongProperty("imageId").getProperty();
        poi.addToOne(image, POIImageProperty);

        de.greenrobot.daogenerator.DaoGenerator generator = new de.greenrobot.daogenerator.DaoGenerator();
        generator.generateAll(schema, "../LifeHelper/app/src/main/java");
    }

}
