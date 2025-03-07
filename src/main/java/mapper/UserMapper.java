package mapper;

import model.User;
import view.model.UserDTO;
import view.model.builder.UserDTOBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO convertUserToUserDTO(User user) {
        return new UserDTOBuilder().setUsername(user.getUsername()).setRole(user.getRoles().getFirst().getRole()).build(); //??
    }

    public static List<UserDTO> convertUserDTOToListToUserDTOList(List<User> users) {
        return users.parallelStream().map(UserMapper::convertUserToUserDTO).collect(Collectors.toList());
    }
}
