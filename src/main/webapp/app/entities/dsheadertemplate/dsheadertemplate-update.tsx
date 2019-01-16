import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './dsheadertemplate.reducer';
import { IDsheadertemplate } from 'app/shared/model/dsheadertemplate.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import CKEditor from "react-ckeditor-component";

export interface IDsheadertemplateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> { }

export interface IDsheadertemplateUpdateState {
  isNew: boolean;
  ckEditorContent: string;
  isFlag :boolean
}


const toolbarConfig = {
  height: "250px",
  toolbar: 'Full',
  allowedContent: true,
  startupFocus: true,
  // toolbarGroups: [
  //   { name: 'document', groups: ['mode', 'document', 'doctools'] },
  //   { name: 'clipboard', groups: ['clipboard', 'undo'] },
  //   { name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing'] },
  //   { name: 'forms', groups: ['forms'] },
  //   { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
  //   { name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph'] },
  //   { name: 'links', groups: ['links'] },
  //   { name: 'insert', groups: ['insert'] },
  //   { name: 'styles', groups: ['styles'] },
  //   { name: 'colors', groups: ['colors'] },
  //   { name: 'tools', groups: ['tools'] },
  //   { name: 'others', groups: ['others'] },
  //   { name: 'about', groups: ['about'] }
  // ],
  // removeButtons: 'PasteFromWord,Image,Source,Save,NewPage,Preview,Templates,Cut,Copy,Paste,PasteText,Find,Replace,SelectAll,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,Strike,Subscript,Superscript,CopyFormatting,RemoveFormat,Outdent,Indent,Blockquote,CreateDiv,BidiLtr,BidiRtl,Link,Unlink,Anchor,Flash,Smiley,SpecialChar,PageBreak,Iframe,Styles,Maximize,ShowBlocks,About,Language',
  // removePlugins: 'elementspath',
};

export class DsheadertemplateUpdate extends React.Component<IDsheadertemplateUpdateProps, IDsheadertemplateUpdateState> {
  constructor(props) {
    super(props);
    this.saveEntity = this.saveEntity.bind(this);
    this.onChange = this.onChange.bind(this);
    this.state = {
      isFlag:true,
      ckEditorContent: '',
      isNew: !this.props.match.params || !this.props.match.params.id

    };
  }

  onChange(evt) {
    console.log("onChange fired with event info: ", evt);
    var newContent = evt.editor.getData();
    this.setState({
      ckEditorContent: newContent,
      isFlag : false
    })
  }
  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
      const { dsheadertemplateEntity } = this.props;
      console.log(dsheadertemplateEntity);
    }
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { dsheadertemplateEntity } = this.props;
      values.headercontent = this.state.ckEditorContent;
      const entity = {
        ...dsheadertemplateEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/dsheadertemplate');
  };

  render() {
    const { dsheadertemplateEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    const { headercontent } = dsheadertemplateEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="fireFlyCaseManagerAdminApp.dsheadertemplate.home.createOrEditLabel">Create or Edit Header Template</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
                <AvForm model={isNew ? {} : dsheadertemplateEntity} onSubmit={this.saveEntity}>
                  {!isNew ? (
                    <AvGroup>
                      <Label for="id">ID</Label>
                      <AvInput id="dsheadertemplate-id" type="text" className="form-control" name="id" required readOnly />
                    </AvGroup>
                  ) : null}
                  {/* <AvGroup>
                    <Label id="headercontentLabel" for="headercontent">
                      Headercontent
                  </Label>
                    <AvInput
                      id="dsheadertemplate-headercontent"
                      type="textarea"
                      name="headercontent"
                      validate={{
                        required: { value: true, errorMessage: 'This field is required.' }
                      }}
                    />
                  </AvGroup> */}
                  <AvGroup>
                    <Label id="headertemplatenameLabel" for="headertemplatename">
                      Headertemplatename
                  </Label>
                    <AvField
                      id="dsheadertemplate-headertemplatename"
                      type="text"
                      name="headertemplatename"
                      validate={{
                        required: { value: true, errorMessage: 'This field is required.' }
                      }}
                    />
                  </AvGroup>
                  <AvGroup>
                    <Label id="headercontentLabel" for="headercontent">
                      Headercontent
                  </Label>
                    <div>
                      {/* {this.state.ckEditorContent} */}
                      <CKEditor
                        activeClass="p10"
                        config={toolbarConfig}
                        content={this.state.isFlag? dsheadertemplateEntity.headercontent: this.state.ckEditorContent}
                        events={{
                          "change": this.onChange
                        }}
                      />
                    </div>
                  </AvGroup>

                  

                  <Button tag={Link} id="cancel-save" to="/entity/dsheadertemplate" replace color="info">
                    <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                  </Button>
                  &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                    <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
                </AvForm>
              )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  dsheadertemplateEntity: storeState.dsheadertemplate.entity,
  loading: storeState.dsheadertemplate.loading,
  updating: storeState.dsheadertemplate.updating,
  updateSuccess: storeState.dsheadertemplate.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DsheadertemplateUpdate);
