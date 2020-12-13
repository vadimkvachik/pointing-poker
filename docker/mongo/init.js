db.createUser(
    {
        user: "pp_user",
        pwd: "pp_password",
        roles:[
            {
                role: "readWrite",
                db: "pointing-poker"
            }
        ]
    }
);