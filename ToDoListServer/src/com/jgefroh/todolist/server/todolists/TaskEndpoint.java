package com.jgefroh.todolist.server.todolists;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@RequestScoped
@Path("/tasks")
public class TaskEndpoint {
    
    @Inject private ToDoListManager listManager;

    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public List<Task> getTasks(@QueryParam("ownerId") final String ownerId,
                                @QueryParam("completed") final Boolean gettingCompletedTasks) {
        if (Boolean.TRUE.equals(gettingCompletedTasks)) {
            return listManager.getCompleteTasks(ownerId);
        }
        else if (Boolean.FALSE.equals(gettingCompletedTasks)) {
            return listManager.getIncompleteTasks(ownerId);
        }
        else {
            return listManager.getTasks(ownerId);
        }
    }
    
    @PUT
    @Consumes("application/json")
    public Task saveTask(@QueryParam("ownerId") final String ownerId, final Task task) {
        if (task.getId() == null) {
            return listManager.createTask(ownerId, task.getName(), task.getGroup());
        }
        else {
            return listManager.updateTask(ownerId, task.getId(), task.getName(), task.getGroup());
        }
    }
    
    @PUT
    @Path("/{taskId}/{ownerId}/markComplete")
    public Task markComplete(@PathParam("ownerId") final String ownerId,
                             @PathParam("taskId") final int taskId) {
        return listManager.markComplete(ownerId, taskId);
    }
    
    @PUT
    @Path("{ownerId}/task/{taskId}/markIncomplete")
    public Task markIncomplete(@PathParam("ownerId") final String ownerId,
                               @PathParam("taskId") final int taskId) {
        return listManager.markIncomplete(ownerId, taskId);
    }
    
    @PUT
    @Path("/{taskId}/{ownerId}/track")
    public Task trackTask(@PathParam("ownerId") final String ownerId,
                          @PathParam("taskId") final int taskId) {
        return listManager.trackTask(ownerId, taskId);
    }
    
    @PUT
    @Path("/{taskId}/{ownerId}/untrack")
    public Task untrackTask(@PathParam("ownerId") final String ownerId,
                            @PathParam("taskId") final int taskId) {
        return listManager.untrackTask(ownerId, taskId);
    }
}