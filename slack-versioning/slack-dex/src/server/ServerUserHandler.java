package server;

class ServerUserHandler implements Runnable {

    @Override
    public void run() {
    }
}

/*
    private Server server;
    private ServerUser serverUser;

    public ServerUserHandler(Server server, ServerUser serverUser) {
        this.server = server;
        this.serverUser = serverUser;
        //this.server.broadcastAllServerUsers();
    }

    public void run() {
        String message;

        // when there is a new message, broadcast to all
        Scanner sc = new Scanner(this.serverUser.getInputStream());
        while (sc.hasNextLine()) {
            message = sc.nextLine();

            // smiley
            message = message.replace(":)", "<img src='../resources/img/smiley/smiley1.png'>");
            message = message.replace("xd", "<img src='../resources/img/smiley/smiley2.png'>");
            message = message.replace("xD", "<img src='../resources/img/smiley/smiley2.png'>");
            message = message.replace(":))", "<img src='../resources/img/smiley/smiley2.png'>");
            message = message.replace(":d", "<img src='../resources/img/smiley/smiley2.png'>");
            message = message.replace(":D", "<img src='../resources/img/smiley/smiley2.png'>");
            message = message.replace(":/", "<img src='../resources/img/smiley/smiley3.png'>");
            message = message.replace(":(", "<img src='../resources/img/smiley/smiley3.png'>");
            message = message.replace("-_-", "<img src='../resources/img/smiley/smiley4.png'>");
            message = message.replace(";)", "<img src='../resources/img/smiley/smiley5.png'>");
            message = message.replace(":p", "<img src='../resources/img/smiley/smiley6.png'>");
            message = message.replace(":P", "<img src='../resources/img/smiley/smiley6.png'>");
            message = message.replace(":o", "<img src='../resources/img/smiley/smiley7.png'>");
            message = message.replace(":O", "<img src='../resources/img/smiley/smiley7.png'>");

            // Gestion des messages private
            if (message.charAt(0) == '@'){
                if(message.contains(" ")){
                    System.out.println("private msg : " + message);
                    int firstSpace = message.indexOf(" ");
                    String userPrivate= message.substring(1, firstSpace);
                    //server.sendMessageToUser(
                     //       message.substring(
                       //             firstSpace+1, message.length()
                         //   ), serverUser, userPrivate
                    //);
                }

                // Gestion du changement
            }else if (message.charAt(0) == '#'){
                serverUser.changeColor(message);
                // update color for all other users
                //this.server.broadcastAllServerUsers();
            }else{
                // update user list
                //server.broadcastMessages(message, serverUser);
            }
        }
        // end of Thread
        //server.removeUser(serverUser);
        //this.server.broadcastAllServerUsers();
        sc.close();
    }
}
*/
