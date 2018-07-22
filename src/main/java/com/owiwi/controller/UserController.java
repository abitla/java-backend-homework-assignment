package com.owiwi.controller;

import com.owiwi.enumeration.ConnectionType;
import com.owiwi.model.Connection;
import com.owiwi.model.SearchRequest;
import com.owiwi.model.User;
import com.owiwi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private static final String USER_ID = "/user/{id}";
    private static final String CONNECTIONS_ID = "/connections/{id}";
    private static final String CONNECTIONS_ADD = "/connections/{type}";
    private static final String CONNECTION_SUGGESTIONS = "/getSuggestions";

    @Autowired
    UserService userService;

    /** get user by id **/
    @RequestMapping(
            value = USER_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    /** get connections of the user by id **/
    @RequestMapping(
            value = CONNECTIONS_ID,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<ConnectionType, List<Connection>>> getConnections(@PathVariable("id") long id) {

        List<Connection> connections = userService.getConnections(id);
        List<ConnectionType> connectionTypeList = Arrays.asList(ConnectionType.values());
        Map<ConnectionType, List<Connection>> connectionsByType = new HashMap<ConnectionType, List<Connection>>();

        // add connections to map based on connection type
        connectionTypeList.forEach( connectionType -> {
                    List<Connection> connectionsTypeList = connections
                            .stream()
                            .filter(connection -> connection.getType().equals(connectionType))
                            .collect(Collectors.toList());
                    connectionsByType.put(connectionType, connectionsTypeList);
                }
        );

        return ResponseEntity.ok(connectionsByType);
    }

    /** add connections for users based on connection type**/
    @RequestMapping(
            value = CONNECTIONS_ADD,
            method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> addConnections(@PathVariable("type") ConnectionType connectionType,
                                             @RequestBody List<String> usersIds) {
        List<User> users = new ArrayList<User>();
        usersIds.forEach( userId -> {
                    users.add(userService.getUser(Long.parseLong(userId)));
                });
        userService.addConnections(connectionType, users);

        return ResponseEntity.ok("success");
    }

    /** get suggestions for potential mentors based on search preferences **/
    @RequestMapping(
            value = CONNECTION_SUGGESTIONS,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<User>> getSuggestions(
            @RequestBody SearchRequest searchRequest) {
        User user = userService.getUser(Long.parseLong(searchRequest.getId())); // get requesting user
        List<User> users = userService.getUsersByFilters(searchRequest, user); // get users based on search filters
        List<User> filteredUsers = new ArrayList<User>(); // final filtered list to return

        for (int i = 0; users != null && i < users.size(); i++)
        {
            //get connections for filtered users
            List<Connection> filterUserConnections = userService.getConnections(users.get(i).getId());

            // get connections for requesting user
            List<Connection> requestingUserConnections = userService.getConnections(Long.parseLong(searchRequest.getId()));

            // flag to determine if there is stranger connection
            boolean hasStrangerConnection = false;

            // check if filtered users marked the requesting user as stranger // TODO: get it peer reviewed
            for (int j = 0; filterUserConnections != null && j < filterUserConnections.size(); j++)
            {
                if (filterUserConnections.get(j).getType().toString().equals(ConnectionType.STRANGER.toString())
                    && ( filterUserConnections.get(j).getFromId() == user.getId()
                    || filterUserConnections.get(j).getToId() == user.getId() )
                        )
                {
                   hasStrangerConnection = true; // has a connection
                }
            }

            // check if requesting user marked the filtered user as a stranger //TODO: get it peer reviewed
            for (int k = 0; requestingUserConnections != null && k < requestingUserConnections.size(); k++)
            {
                if (requestingUserConnections.get(k).getType().toString().equals(ConnectionType.STRANGER.toString())
                        && ( requestingUserConnections.get(k).getFromId() == user.getId()
                        || requestingUserConnections.get(k).getToId() == user.getId() )
                        )
                {
                    hasStrangerConnection = true; // has a connection
                }
            }

            // if no stranger connection, add filtered user to the final list
            if (!hasStrangerConnection)
            {
                filteredUsers.add(users.get(i));
            }
        }

        return ResponseEntity.ok(filteredUsers);
    }

}