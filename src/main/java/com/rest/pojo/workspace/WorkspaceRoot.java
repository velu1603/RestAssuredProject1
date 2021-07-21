package com.rest.pojo.workspace;

public class WorkspaceRoot {
    Workspace workspace;

    public WorkspaceRoot(){

    }
    public WorkspaceRoot(Workspace workspace){
        this.workspace = workspace;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkSpace(Workspace workspace) {
        this.workspace = workspace;
    }
}
