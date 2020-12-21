package Borman.cbbbluechips.utilities;

import Borman.cbbbluechips.models.usergroups.Group;
import Borman.cbbbluechips.models.usergroups.GroupCreationRequest;
import Borman.cbbbluechips.models.usergroups.UserGroup;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserGroupUtility {

    public static List<UserGroup> combineJoinedAndOpenGroupLists(List<UserGroup> joined, List<UserGroup> open) {
        List<UserGroup> x =  Stream.concat(joined.stream(), open.stream()
        ).sorted(
                Comparator.comparing(UserGroup::isUserJoinedGroup))
                .collect(Collectors.toList()
        );

       return Lists.reverse(x);
    }

    public static boolean isPasswordRequired(GroupCreationRequest request) {
        return !StringUtils.isEmpty(request.getGroupPassword());
    }

    public static String buildInviteLink(Group activeGroup) {
        return "https://www.cbb-bluechips.com/group/invite/" + activeGroup.getGroupId();
    }

}