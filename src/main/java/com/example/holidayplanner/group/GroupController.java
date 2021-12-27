package com.example.holidayplanner.group;

import com.example.holidayplanner.interfaces.ControllerInterface;
import com.example.holidayplanner.user.User;
import com.example.holidayplanner.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1.0/groups")
public class GroupController implements ControllerInterface<Group> {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) { this.groupService = groupService; }

    @Override
    @PostMapping(path = "/newgroup")
    public String create(Group group, Errors errors) {
        return groupService.create(group);
    }

    @Override
    @GetMapping
    public List<Group> getAll() { return groupService.getAll();}

    @Override
    public String update(String id, Group newInfo) {
        return null;
    }

    @Override
    public String delete(String id) {
        return null;
    }

    @PostMapping(path = "/addmember/{groupId}")
    public String addGroupMember(@PathVariable("groupId") String groupId, @RequestBody String userId) {
        return groupService.addGroupMember(groupId, userId);
    }

    @PostMapping(path = "/removemember/{groupId}")
    public String removeGroupMember(@PathVariable("groupId") String groupId, @RequestBody String userId){
        return groupService.removeGroupMember(groupId, userId);
    }
}
