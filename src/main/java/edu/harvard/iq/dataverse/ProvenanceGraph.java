package edu.harvard.iq.dataverse;

import edu.harvard.iq.dataverse.authorization.AuthenticationServiceBean;
import edu.harvard.iq.dataverse.authorization.DataverseRole;
import edu.harvard.iq.dataverse.authorization.Permission;
import edu.harvard.iq.dataverse.authorization.RoleAssignee;
import edu.harvard.iq.dataverse.authorization.RoleAssigneeDisplayInfo;
import edu.harvard.iq.dataverse.authorization.groups.Group;
import edu.harvard.iq.dataverse.authorization.groups.GroupException;
import edu.harvard.iq.dataverse.authorization.groups.GroupServiceBean;
import edu.harvard.iq.dataverse.authorization.groups.impl.builtin.AuthenticatedUsers;
import edu.harvard.iq.dataverse.authorization.groups.impl.explicit.ExplicitGroup;
import edu.harvard.iq.dataverse.authorization.groups.impl.explicit.ExplicitGroupServiceBean;
import edu.harvard.iq.dataverse.authorization.users.AuthenticatedUser;
import edu.harvard.iq.dataverse.engine.command.exception.CommandException;
import edu.harvard.iq.dataverse.engine.command.exception.PermissionException;
import edu.harvard.iq.dataverse.engine.command.impl.AssignRoleCommand;
import edu.harvard.iq.dataverse.engine.command.impl.CreateExplicitGroupCommand;
import edu.harvard.iq.dataverse.engine.command.impl.CreateRoleCommand;
import edu.harvard.iq.dataverse.engine.command.impl.RevokeRoleCommand;
import edu.harvard.iq.dataverse.engine.command.impl.UpdateDataverseDefaultContributorRoleCommand;
import edu.harvard.iq.dataverse.util.JsfHelper;
import static edu.harvard.iq.dataverse.util.JsfHelper.JH;
import edu.harvard.iq.dataverse.util.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author dianne
 */
@ViewScoped
@Named
public class ProvenanceGraph {
    
    @EJB
    DvObjectServiceBean dvObjectService;
    @EJB
    DataverseRoleServiceBean roleService;
    @EJB
    RoleAssigneeServiceBean roleAssigneeService;
    @EJB
    PermissionServiceBean permissionService;
    @EJB
    AuthenticationServiceBean authenticationService;
    @EJB 
    GroupServiceBean groupService;
    @EJB
    EjbDataverseEngine commandEngine;
    
    @PersistenceContext(unitName = "VDCNet-ejbPU")
    EntityManager em;

    @Inject
    DataverseSession session;

    DvObject dvObject = new Dataverse(); // by default we use a Dataverse, but this will be overridden in init by the findById
    Dataverse dv;

    public DvObject getDvObject() {
        return dvObject;
    }

    public void setDvObject(DvObject dvObject) {
        this.dvObject = dvObject;
        /*if (dvObject instanceof DvObjectContainer) {
         inheritAssignments = !((DvObjectContainer) dvObject).isPermissionRoot();
         }*/
    }

    public String init() {
        //@todo deal with any kind of dvObject
        if (dvObject.getId() != null) {
            dvObject = dvObjectService.findDvObject(dvObject.getId());
        }

        // check if dvObject exists and user has permission
        if (dvObject == null) {
            return "/404.xhtml";
        }

        // for dataFiles, check the perms on its owning dataset
        DvObject checkPermissionsdvObject = dvObject instanceof DataFile ? dvObject.getOwner() : dvObject;
        if (!permissionService.on(checkPermissionsdvObject).has(checkPermissionsdvObject instanceof Dataverse ? Permission.ManageDataversePermissions : Permission.ManageDatasetPermissions)) {
            return "/loginpage.xhtml" + DataverseHeaderFragment.getRedirectPage();
        }

        // initialize the configure settings
        if (dvObject instanceof Dataverse) {
            dv = (Dataverse) dvObject;
            //dv.getMetadataDataverseNameList();
        }
        return "";
    }
    
    public ArrayList<String> getNames() {
        return dv.getMetadataDataverseNameList();
    }
    
    
}
