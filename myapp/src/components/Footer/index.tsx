import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';
import {PLANET_LINK} from "@/constants";

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      links={[
        {
          key: 'github',
          title: <><GithubOutlined />洛言 GitHub</>,
          href: 'https://github.com/Epiphany6666',
          blankTarget: true,
        },
        {
          key: 'CSDN',
          title: 'CSDN',
          href: PLANET_LINK,
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
